package org.luchini.treeview.parser;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

import org.luchini.treeview.annotations.TreeAttribute;
import org.luchini.treeview.annotations.TreeNode;
import org.luchini.treeview.model.Attribute;
import org.luchini.treeview.model.Node;

public class TreeViewBuilder {

	private TreeViewParser parser;
	
	public TreeViewBuilder(TreeViewParser parser) {
		this.parser = parser;
	}
	
	public String process(Object instance) {
		String out = null;
		
		Node node = this.processLevel(null, instance, null);
		out = this.parser.parse(node);
		
		return out;
	}
	
	private Node processLevel(Node nodeParent, Object instance, String strongerName) {
		Node myNode = null;
		
		Class<?> clazz = instance.getClass();
		TreeNode treeNode = clazz.getAnnotation(TreeNode.class);
		if (treeNode != null) {
			myNode = new Node();
			myNode.setName(this.getNodeName(treeNode, clazz));
			this.addFixedAttributes(treeNode, myNode);
			Field[] fields = clazz.getDeclaredFields();
			boolean isBrokenDown = false;
			for (Field field : fields) {
				TreeAttribute treeAttribute = field.getAnnotation(TreeAttribute.class);
				TreeNode fieldNode = field.getAnnotation(TreeNode.class);
				Class<?>[] interfaces = field.getType().getInterfaces();
				boolean isCollection = false;
				for (Class<?> inter : interfaces) {
					if (inter.equals(Iterable.class) || 
							inter.equals(Collection.class)) {
						isCollection = true;
						break;
					}
				}
				if (treeAttribute != null) {
					this.addAttributeFromField(myNode, treeAttribute, field, instance);
					if (treeNode.attributesOnly())
						isBrokenDown = true;
				}
				if (fieldNode != null) {
					if(myNode.getChildren() == null)
						myNode.setChildren(new ArrayList<Node>());
					if (!isCollection) {
						myNode.getChildren().add(
								this.processLevel(
										myNode, 
										this.getFieldValue(field, instance), 
										this.getNodeName(treeNode, field)));
					} else {
						Node colNode = new Node(this.getNodeName(fieldNode, field));
						Object value = this.getFieldValue(field, instance);
						if (!value.equals("null")) {
							Collection<?> collection = (Collection<?>)this.getFieldValue(field, instance);
							if (collection != null) {
								colNode.setChildren(new ArrayList<Node>());
								for (Object obj : collection) {
									colNode.getChildren().add(this.processLevel(colNode, obj, obj.getClass().getSimpleName()));
								}
							}
						}
						myNode.getChildren().add(colNode);
					}
					isBrokenDown = true;
				}
			}
			if (!isBrokenDown) {
				myNode.setValue(instance.toString());
			}
		} else {
			myNode = new Node(strongerName);
			myNode.setValue(instance.toString());
		}
	
		return myNode;
	}
	
	private String getNodeName(TreeNode treeNode, Class<?> clazz) {
		return (treeNode.alias().length() != 0) ? treeNode.alias() : clazz.getSimpleName();
	}
	
	private String getNodeName(TreeNode treeNode, Field field) {
		return (treeNode.alias().length() != 0) ? treeNode.alias() : field.getName();
	}
	
	private String getAttributeName(TreeAttribute treeAttribute, Field field) {
		return (treeAttribute.alias().length() != 0) ? treeAttribute.alias() : field.getName();
	}
	
	private Object getFieldValue(Field field, Object instance) {
		Object out = null;
		field.setAccessible(true);
		try {
			out = field.get(instance);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		if (out == null)
			out = "null";
		return out;
	}
	
	private void addAttributeFromField(Node node, TreeAttribute treeAttribute, Field field, Object instance) {
		if (node.getAttributes() == null)
			node.setAttributes(new ArrayList<Attribute>());
		node.getAttributes().add(new Attribute(
				this.getAttributeName(treeAttribute, field),
				this.getFieldValue(field, instance).toString()));
	}
	
	private void addFixedAttributes(TreeNode treeNode, Node myNode) {
		if(treeNode.fixedAttributes().length > 0 && treeNode.fixedAttributes().length == treeNode.fixedAttributesValues().length) {
			if (myNode.getAttributes() == null)
				myNode.setAttributes(new ArrayList<Attribute>());
			for (int i=0; i<treeNode.fixedAttributes().length; i++) {
				myNode.getAttributes().add(new Attribute(treeNode.fixedAttributes()[i], treeNode.fixedAttributesValues()[i]));
			}				
		}
	}
	
}
