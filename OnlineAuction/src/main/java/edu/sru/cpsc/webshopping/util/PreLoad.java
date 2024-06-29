package edu.sru.cpsc.webshopping.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import edu.sru.cpsc.webshopping.domain.widgets.Category;
import edu.sru.cpsc.webshopping.repository.widgets.CategoryRepository;

@Component
public class PreLoad {

  private final CategoryRepository categoryRepository;

  @Autowired
  public PreLoad(CategoryRepository categoryRepository) {
      this.categoryRepository = categoryRepository;
  }

  private int countLines(InputStream is) throws IOException {
    int lines = 0;
    try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
      while (br.readLine() != null) {
        lines++;
      }
    }
    return lines;
  }


  /*
   * Import categories from a CSV file. CSV file from: https://support.google.com/merchants/answer/6324436?hl=en-GB
   * @return void
   */
  public void importCategoriesFromCSV() {
    Resource resource = new ClassPathResource("static/preload/category-tree.csv");

    Map<String, Category> categoryMap = new HashMap<>();

    int totalLines;
    try (InputStream is = resource.getInputStream()) {
      totalLines = countLines(is);
    } catch (IOException e) {
      e.printStackTrace();
      return;
    }

    try (InputStream inputStream = resource.getInputStream();
      BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
      Spinner spinner = new Spinner()) {

      String line;
      int currentLine = 0;
      while ((line = br.readLine()) != null) {
        currentLine++;
        System.out.print("\rSQL Import " + currentLine + " / " + totalLines + " " + spinner.getCurrentSpinnerChar());
        // Check for BOM and remove it
        if (line.startsWith("\uFEFF")) {
            line = line.substring(1);
        }
        String[] values = line.split(",");
        
        // Filter out empty values and get the category and its parent
        List<String> nonEmptyValues = Arrays.stream(values).filter(v -> !v.trim().isEmpty()).collect(Collectors.toList());
        String categoryName = nonEmptyValues.get(nonEmptyValues.size() - 1);
        String parentName = nonEmptyValues.size() > 1 ? nonEmptyValues.get(nonEmptyValues.size() - 2) : null;

        Category category = new Category(categoryName);

        // If the category has a parent
        if (parentName != null) {
            Category parentCategory = categoryMap.get(parentName);
            category.setParent(parentCategory);
        }

        // Persist the category to the database
        categoryRepository.save(category);

        // Add the category to the map
        categoryMap.put(categoryName, category);
      }
    } catch (Exception e) {
        e.printStackTrace();
    }
  }


  public static String getDefaultCountryCode() {
    Resource resource = new ClassPathResource("static/preload/default_country.txt");

    try (InputStream inputStream = resource.getInputStream();
        Scanner sc = new Scanner(inputStream)) {
        return sc.nextLine().trim();
    } catch (IOException e) {
        throw new RuntimeException("Failed to load default country code", e);
    }
  }

}
