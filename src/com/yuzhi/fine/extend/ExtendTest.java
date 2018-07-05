package com.yuzhi.fine.extend;

public class ExtendTest {

	public static void main(String[] args) {
		
		ChildA a = new ChildA();
		ChildB b = new ChildB();
		
		sayName(a);
		sayName(b);

		System.out.println((new Parent()).hashCode());
		System.out.println((new Parent()).hashCode());
		System.out.println(new Parent());
		System.out.println(new Parent());

	}
	
	public static void sayName(Parent p) {
		if(p instanceof ChildA) {
			System.out.print("-A-");
			ChildA a = (ChildA)p;
			a.sayA();
		} else if(p instanceof ChildB) {
			System.out.print("-B-");
			ChildB b = (ChildB)p;
			b.sayB();
		}
		p.sayName();
	}
	
}
