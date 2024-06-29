package edu.sru.cpsc.webshopping.domain.widgets;

public class AttributeFormEntry {
	private Attribute attribute;
	private WidgetAttribute widgetAttribute;
	
	public AttributeFormEntry() {}

	public AttributeFormEntry(Attribute attribute, WidgetAttribute widgetAttribute) {
		this.attribute = attribute;
		this.widgetAttribute = widgetAttribute;
	}

	public WidgetAttribute getWidgetAttribute() {
		return widgetAttribute;
	}
	public void setWidgetAttribute(WidgetAttribute widgetAttribute) {
		this.widgetAttribute = widgetAttribute;
	}
	public Attribute getAttribute() {
		return attribute;
	}
	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}

	public String toString() {
		return String.format("AttributeFormEntry(attribute=%s, widgetAttribute=%s)", attribute, widgetAttribute);
	}
}