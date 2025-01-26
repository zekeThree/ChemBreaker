package ChemBreakerPackage;

import java.util.*;

public class Main {
	//this one
	private static ArrayList<Integer> reactents = new ArrayList<Integer>();
	private static ArrayList<Integer> products = new ArrayList<Integer>();
	private static ArrayList<Integer> reCoefs = new ArrayList<Integer>();
	private static ArrayList<Integer> prCoefs = new ArrayList<Integer>();
	private static ArrayList<Integer> balanceCoefs = new ArrayList<Integer>();

	public static void main(String[] args) {
		String answerBook = "";
		boolean products = false;
		// CH + 2O > CO2 + 2H2O
//		String[] El1 = { "Fe", "S" };
//		int[] Qu1 = { 1, 2 };
//		Molecule R1 = new Molecule(El1, Qu1, true);
//		String[] El2 = { "O", };
//		int[] Qu2 = { 2 };
//		Molecule R2 = new Molecule(El2, Qu2, true);
//
//		String[] El4 = { "Fe", "O" };
//		int[] Qu4 = { 2, 4 };
//		Molecule P1 = new Molecule(El4, Qu4, false);
//		String[] El5 = { "S", "O" };
//		int[] Qu5 = { 1, 2 };
//		Molecule P2 = new Molecule(El5, Qu5, false);
		ArrayList<String> argElem = new ArrayList<>();
		ArrayList<Integer> argCoef = new ArrayList<>();
		ArrayList<Molecule> recat = new ArrayList<Molecule>();
		ArrayList<Molecule> prod = new ArrayList<Molecule>();

		Scanner book = new Scanner(System.in);
		System.out.println("type m go to the next molucule, p to swich to products  and t to finsh ");

		while (true) {
			System.out.println(" type the element name ");
			argElem.add(book.next());
			System.out.println(" type the ratio to molucule ");
			argCoef.add(book.nextInt());
			System.out.println("	next element(x) | add molucule(m) | products(p) | end(t) ");
			System.out.println(recat.subList(0, recat.size()));
			
			answerBook = book.next();
			if (answerBook.equals("x")) {

			} else if (answerBook.equals("m")) {
				if (products) {
					recat.add(new Molecule(argElem, argCoef, true));
				} else {
					recat.add(new Molecule(argElem, argCoef, false));
				}
				argElem.clear();
				argCoef.clear();
				//here
			} else if (answerBook.equals("p")) {
				recat.add(new Molecule(argElem, argCoef, false));

				argElem.clear();
				argCoef.clear();
				products = true;
			} else if (answerBook.equals("t")) {
				if (products) {
					recat.add(new Molecule(argElem, argCoef, true));
				} else {
					recat.add(new Molecule(argElem, argCoef, false));
				}
				argElem.clear();
				argCoef.clear();
				break;
			}

		}
		//next fix so reactens swich to products bacesd on boolean isR so reactents can have dif num then the products
		
		//make a method to print the the molules already added
		for (int size = 0; size < Main.findElements(recat).size(); size++) {

			reactents.add(0);
			reCoefs.add(1);
		}
		for (int size = 0; size < Main.findElements(recat).size(); size++) {

			reactents.add(0);
			reCoefs.add(1);
		}
		

//		reCoefs.set(0, 1);
//		reCoefs.set(1, 2);
//		prCoefs.set(0, 1);
//		prCoefs.set(1, 2);

//		made a change- does this change count?
		reCoefs.set(0, 2);
		reCoefs.set(1, 4);
		prCoefs.set(1, 2);
		prCoefs.set(0, 1);
		// System.out.println(isEqual(recat, prod));
		System.out.println(bust(recat, prod, 10));
		System.out.println();
		// System.out.println("balanceCoefs :" + balanceCoefs.subList(0,
		// balanceCoefs.size()));

	}

	public static ArrayList<String> findElements(ArrayList<Molecule> chemEqu) {
		ArrayList<String> Elements = new ArrayList<String>();
		boolean inEl = false;
		String str = chemEqu.get(0).getElements()[0];
		Elements.add(str);
		for (int x = 0; x < chemEqu.size(); x++) {

			for (int n = 0; n < chemEqu.get(x).getElements().length; n++) {
				str = chemEqu.get(x).getElements()[n];
				for (int t = 0; t < Elements.size(); t++) {
					if ((str.equals(Elements.get(t)))) {
						inEl = true;
					}
				}
				if (inEl) {
					inEl = false;
				} else {
					Elements.add(str);
				}
			}
		}
		return Elements;

	}

	public static boolean compair(ArrayList<Integer> re, ArrayList<Integer> pr) {
		boolean ans = true;
		for (int q = 0; q < re.size(); q++) {
			int one = re.get(q);
			int two = pr.get(q);
			if (!(one == two)) {
				ans = false;
			}
		}
		return ans;
	}

	public static ArrayList<Integer> computCoef(ArrayList<Molecule> compounts, ArrayList<Integer> coef) {
		ArrayList<Integer> rePr = new ArrayList<Integer>();
		ArrayList<Integer> coefs = new ArrayList<Integer>();
		ArrayList<String> elements = new ArrayList<String>();
		ArrayList<String> compElems = new ArrayList<String>();
		elements = findElements(compounts);
		for (int elementsSize = 0; elementsSize < elements.size(); elementsSize++) {
			rePr.add(0);
		}

		for (int c = 0; c < compounts.size(); c++) {
			coefs = compounts.get(c).coefficent(coef.get(c));
			for (String elem : compounts.get(c).getElements()) {
				compElems.add(elem);
			}
			for (int addingCoef = 0; addingCoef < coefs.size(); addingCoef++) {

				for (int d = 0; d < elements.size(); d++) {
					if (compElems.get(addingCoef).equals(elements.get(d))) {

						rePr.set(d, (rePr.get(d) + coefs.get(addingCoef)));
					}
				}
			}

			compElems.clear();
		}

		System.out.println("elements :" + elements.subList(0, elements.size()) + " sublist vlaues :"
				+ rePr.subList(0, rePr.size()));
		return rePr;
	}

	public static void addElements(ArrayList<Molecule> Reactents, ArrayList<Molecule> compounts,
			ArrayList<Integer> coefValues, boolean React) {

		ArrayList<String> compountElements = new ArrayList<String>();
		ArrayList<String> compElems = new ArrayList<String>();
		compountElements = Main.findElements(Reactents);
		compElems = Main.findElements(compounts);
		if (React) {

			for (int addingCoef = 0; addingCoef < coefValues.size(); addingCoef++) {

				for (int d = 0; d < compountElements.size(); d++) {

					if (compElems.get(addingCoef).equals(compountElements.get(d))) {

						reactents.set(d, coefValues.get(addingCoef));
					}
				}
			}

		} else {
			for (int addingCoef = 0; addingCoef < coefValues.size(); addingCoef++) {

				for (int d = 0; d < compountElements.size(); d++) {

					if (compElems.get(addingCoef).equals(compountElements.get(d))) {

						products.set(d, coefValues.get(addingCoef));
					}
				}
			}

		}
		System.out.println(compountElements.subList(0, compountElements.size()));

	}

	public static ArrayList<Integer> bust(ArrayList<Molecule> recat, ArrayList<Molecule> prod, int maxCoef) {

		for (int balanceCoefsSize = 0; balanceCoefsSize < ((reactents.size() - 1) * 2); balanceCoefsSize++) {
			balanceCoefs.add(1);
		}
		System.out.println("first " + balanceCoefs.subList(0, balanceCoefs.size()));

		reCoefs = Main.split(balanceCoefs, true);
		prCoefs = Main.split(balanceCoefs, false);
		if (Main.isEqual(recat, prod)) {
			return balanceCoefs;
		}

		Main.sum(recat, prod, balanceCoefs.size() - 1, maxCoef);

		return balanceCoefs;
	}

	public static void sum(ArrayList<Molecule> recat, ArrayList<Molecule> prod, int k, int maxCoef) {

		if (k > 0) {
			if (balanceCoefs.get(k - 1) >= maxCoef) {
				balanceCoefs.set(k, balanceCoefs.get(k) + 1);
				balanceCoefs.set(k - 1, 0);
			}
			reCoefs = Main.split(balanceCoefs, true);
			prCoefs = Main.split(balanceCoefs, false);

			if (!Main.isEqual(recat, prod)) {

				sum(recat, prod, k - 1, maxCoef);

			} else {
				System.out.println("reached end #1");
			}

		} else {

			for (int q = 0; q < maxCoef; q++) {

				if (balanceCoefs.get(0) >= maxCoef) {
					balanceCoefs.set(k + 1, balanceCoefs.get(k + 1) + 1);
					balanceCoefs.set(0, 1);
				}
				reCoefs = Main.split(balanceCoefs, true);
				prCoefs = Main.split(balanceCoefs, false);

				if (Main.isEqual(recat, prod)) {
					System.out.println("reached end #2");
					break;
				}
				System.out.println(balanceCoefs.subList(0, balanceCoefs.size()));
				balanceCoefs.set(0, balanceCoefs.get(0) + 1);
			}
			if (!Main.isEqual(recat, prod)) {
				System.out.println(balanceCoefs.subList(0, balanceCoefs.size()));
				sum(recat, prod, balanceCoefs.size() - 1, maxCoef);
			}
//			balanceCoefs.set(0, balanceCoefs.get(0) + 1);
//			reCoefs = Main.split(balanceCoefs, true);
//			prCoefs = Main.split(balanceCoefs, false);
//			if (!Main.isEqual(recat, prod)) {
//				System.out.println("	balanceCoefs: " + balanceCoefs.subList(0, balanceCoefs.size()));
//				sum(recat, prod, balanceCoefs.size() - 1, maxCoef);
//			}

		}

	}

	public static ArrayList<Integer> split(ArrayList<Integer> toBeSpl, boolean front) {
		ArrayList<Integer> part = new ArrayList<Integer>();
		for (int half = 0; half < (toBeSpl.size() / 2); half++) {
			if (front) {
				part.add(toBeSpl.get(half));
			} else {
				part.add(toBeSpl.get(half + (toBeSpl.size() / 2)));
			}
		}
//		System.out.println("part " + part.subList(0, part.size() ));
//		System.out.println("toBeSpl " + toBeSpl.subList(0, toBeSpl.size() ));
		return part;
	}

	public static boolean isEqual(ArrayList<Molecule> recat, ArrayList<Molecule> prod) {
		System.out.println("isEqual-----------");
		Main.addElements(recat, recat, Main.computCoef(recat, reCoefs), true);
		System.out.println("Reactents :" + reactents.subList(0, reactents.size()));
		Main.addElements(recat, prod, Main.computCoef(prod, prCoefs), false);
		System.out.println("Products :" + products.subList(0, products.size()));
		System.out.println("isEqual-----------");
		return Main.compair(products, reactents);
	}
}
