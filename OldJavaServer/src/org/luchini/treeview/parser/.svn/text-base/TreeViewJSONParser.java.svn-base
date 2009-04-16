package org.luchini.treeview.parser;

import org.luchini.treeview.model.Attribute;
import org.luchini.treeview.model.Node;

public class TreeViewJSONParser implements TreeViewParser {

	@Override
	public String parse(Node node) {
		String out = null;
		
		if (node != null) {
			out = "{" + node.getName() + ": ";
			boolean putSomething = false;
			if (node.getAttributes() != null){
				putSomething = true;
				out += "{";
				int x=0;
				for (Attribute attribute : node.getAttributes()) {
					if (x!=0) out += ", ";
					out += attribute.getName() + ": \"" + attribute.getValue() + "\"";
					x++;
				}
				out += "}";
			}
			if (node.getChildren() != null) {
				if (putSomething) out += ", ";
				int x=0;
				for (Node child : node.getChildren()) {
					if (x!=0) out += ", ";
					out += parse(child);
					x++;
				}
			} else {
				if (node.getValue() != null) {
					out += " \"" + node.getValue() + "\"";
				} else {
					out += "}";
				}
			}
		}
		
		return out;
	}

}
