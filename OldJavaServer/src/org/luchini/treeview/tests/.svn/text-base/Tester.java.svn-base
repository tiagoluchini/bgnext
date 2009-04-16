package org.luchini.treeview.tests;

import java.util.ArrayList;

import org.luchini.treeview.parser.TreeViewBuilder;
import org.luchini.treeview.parser.TreeViewXMLParser;

public class Tester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MainObj obj = new MainObj("myID", "myName");
		SecObj sec = new SecObj("the value");
		obj.setSecObj(sec);
		obj.setSecondos(new ArrayList<SecObj>());
		obj.getSecondos().add(new SecObj("val A"));
		obj.getSecondos().add(new SecObj("val B"));
		obj.getSecondos().add(new SecObj("val C"));
		obj.getSecondos().add(new SecObj("val D"));
		
		
		MainObj2 obj2 = new MainObj2();
		obj2.setName("obj2 name");
		
		TreeViewBuilder builder = new TreeViewBuilder(new TreeViewXMLParser());
		String out = builder.process(obj);
		
		System.out.println(out);
		
		System.out.println(builder.process(obj2));
		
		System.out.println(builder.process(new Interf1Impl()));
	}

}
