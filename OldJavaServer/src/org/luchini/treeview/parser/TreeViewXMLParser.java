package org.luchini.treeview.parser;

import org.luchini.treeview.model.Attribute;
import org.luchini.treeview.model.Node;

public class TreeViewXMLParser implements TreeViewParser {

	@Override
	public String parse(Node node) {
		String out = null;
		
		if (node != null) {
			out = "<" + node.getName();
			if (node.getAttributes() != null) {
				for (Attribute attribute : node.getAttributes()) {
					out += " " + attribute.getName() + "=\"" + attribute.getValue() + "\"";
				}
			}
			if (node.getChildren() != null) {
				out += ">";
				for (Node child : node.getChildren()) {
					out += parse(child);
				}
				out += "</" + node.getName() + ">";
			} else {
				if (node.getValue() != null) {
					out += ">" + node.getValue();
					out += "</" + node.getName() + ">";
				} else {
					out += "/>";
				}
			}
		}
		
		return out;
	}

}
