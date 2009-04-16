package org.luchini.treeview.annotations;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface TreeNode {
	String alias() default "";
	String[] fixedAttributes() default {};
	String[] fixedAttributesValues() default {};
	boolean attributesOnly() default false;
}
