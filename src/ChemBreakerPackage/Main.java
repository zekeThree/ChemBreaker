package ChemBreakerPackage;

import java.util.*;

public class Main extends Thread {
	// holds the values of reactents/products after they are multiplyed by the coefs
	private static ArrayList<Integer> reactents = new ArrayList<Integer>();
	private static ArrayList<Integer> reactentsOld = new ArrayList<Integer>();
	private static ArrayList<Integer> products = new ArrayList<Integer>();
	// hold the reactents coefs
	private static ArrayList<Integer> reCoefs = new ArrayList<Integer>();
	// hold the products coefs
	private static ArrayList<Integer> prCoefs = new ArrayList<Integer>();
	// holds the coefs that balance the equation
	private static ArrayList<Integer> balanceCoefs = new ArrayList<Integer>();
	// used for a feature of sum4
	private static ArrayList<Integer> postValRe = new ArrayList<>();
	private static ArrayList<Integer> postValPr = new ArrayList<>();

	private static ArrayList<Integer> maxCoefs = new ArrayList<>(Arrays.asList(100, 100, 100, 100, 100, 100));

	public static void main(String[] args) {
		String answerBook = "";
		boolean Isproducts = false;

		ArrayList<String> argElem = new ArrayList<>();
		ArrayList<Integer> argCoef = new ArrayList<>();
		ArrayList<Molecule> recat = new ArrayList<Molecule>();
		ArrayList<Molecule> prod = new ArrayList<Molecule>();

		Scanner book = new Scanner(System.in);
		System.out.println("type m go to the next molucule, p to swich to products  and t to finsh ");

		while (true) {
			System.out.println(" 	type the element name ");
			argElem.add(book.next());
			System.out.println("	 type the ratio to molucule ");
			argCoef.add(book.nextInt());
			System.out.println("	next element(x) | add molucul (m) | products(p) | end(t) ");

			answerBook = book.next();
			if (answerBook.equals("x")) {
				Print(argElem, argCoef, recat, prod);

			} else if (answerBook.equals("m")) {
				if (Isproducts) {
					prod.add(new Molecule(argElem, argCoef, true));
				} else {
					recat.add(new Molecule(argElem, argCoef, false));
				}
				argElem.clear();
				argCoef.clear();
				Print(argElem, argCoef, recat, prod);
			} else if (answerBook.equals("p")) {
				prod.add(new Molecule(argElem, argCoef, false));

				argElem.clear();
				argCoef.clear();
				Print(argElem, argCoef, recat, prod);
				Isproducts = true;
			} else if (answerBook.equals("t")) {

				prod.add(new Molecule(argElem, argCoef, true));

				argElem.clear();
				argCoef.clear();
				Print(argElem, argCoef, recat, prod);
				break;
			}

		}

		for (int size = 0; size < Main.findElements(recat).size(); size++) {
			reactents.add(0);
			reactentsOld.add(0);
			postValRe.add(0);
		}
		for (int size = 0; size < recat.size(); size++) {
			reCoefs.add(1);
		}

		for (int size = 0; size < Main.findElements(prod).size(); size++) {
			products.add(0);
			postValPr.add(0);
		}
		for (int size = 0; size < prod.size(); size++) {
			prCoefs.add(1);
		}
	

		 System.out.println(bust(recat, prod));
		System.out.println();

	}

// works/ prints the elements in argElem alongside the subsequent coefficient in argCoef 
//and labels them as reactent or product
	public static void Print(ArrayList<String> argElem, ArrayList<Integer> argCoef, ArrayList<Molecule> recat,
			ArrayList<Molecule> prod) {
		for (int d = 0; d < recat.size(); d++) {
			System.out.print("reactent # " + d + " ");
			for (String str : recat.get(d).getElements()) {
				System.out.print(str + " ");
			}
			System.out.println();
			System.out.print("	     ");
			for (int num : recat.get(d).getQuantity()) {
				System.out.print(num + " ");
			}
			System.out.println();
		}
		System.out.println("-->");

		for (int d = 0; d < prod.size(); d++) {
			System.out.print("product # " + d + " ");
			for (String str : prod.get(d).getElements()) {
				System.out.print(str + " ");
			}
			System.out.println();
			System.out.print("	    ");
			for (int num : prod.get(d).getQuantity()) {
				System.out.print(num + " ");
			}
			System.out.println();
		}
		for (int t = 0; t < argElem.size(); t++) {
			System.out.print("current: " + argElem.get(t) + " " + argCoef.get(t) + "  ");
		}
	}

// works/ returns all of the different elements in an arraylist of molocules- not
// in a esaly repeatable order
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

// works/ compairs two arrayLists of the same size and returns true if all of the corasponding valuse are equal;
//good
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

// works/ gives you the quantity of each element after multiplyed by each molecules respective coefficient 
// in the order findElements(compounts);	
//good
	public static ArrayList<Integer> computCoef(ArrayList<Molecule> compounts, ArrayList<Integer> coef) {
		// holds the end values of the elements quantity
		ArrayList<Integer> rePr = new ArrayList<Integer>();
		// holds the raito of a given element to the number of molecules/compounds
		// AlCl3 -> holds 1 for Al and 3 for Cl
		ArrayList<Integer> coefs = new ArrayList<Integer>();
		// holds the elements string used to match the
		ArrayList<String> elements = new ArrayList<String>();
		// holds the elements in a specific molecule to add their qualtity to the total.
		//e.i 
//		for (String elem : compounts.get(c).getElements()) {
//			compElems.add(elem);
//		}
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
		return rePr;
	}
// works/ sets the instance variable reactents or products dependent on the boolean React
// sets them in the order findElements(Reactents);
//good
	public static void addElements(ArrayList<Molecule> Reactents, ArrayList<Molecule> compounts,
			ArrayList<Integer> coefValues, boolean React) {
		//holds the location of each element determend by Main.findElements(Reactens);
		ArrayList<String> compountElements = new ArrayList<String>();
		//holds the location of each element in the arrayList
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
		System.out.println("	" + compountElements.subList(0, compountElements.size()));

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
	
//in progress
	public static ArrayList<Integer> bust(ArrayList<Molecule> recat, ArrayList<Molecule> prod) {

		for (int balanceCoefsSize = 0; balanceCoefsSize < (recat.size() + prod.size()); balanceCoefsSize++) {
			balanceCoefs.add(1);
		}
//		balanceCoefs.set(0,48);
//		balanceCoefs.set(1,5);
//		balanceCoefs.set(2,24);
//		balanceCoefs.set(3,36);
//		balanceCoefs.set(4,55);
//		balanceCoefs.set(5,24);
		System.out.println("first " + balanceCoefs.subList(0, balanceCoefs.size()));

		reCoefs = Main.split(balanceCoefs, recat, true);
		prCoefs = Main.split(balanceCoefs, recat, false);
		if (Main.isEqual(recat, prod)) {
			return balanceCoefs;
		}

		while (true) {

			Main.sum4(recat, prod, 0, maxCoefs);
			reCoefs = Main.split(balanceCoefs, recat, true);
			prCoefs = Main.split(balanceCoefs, recat, false);
			if (Main.isEqual(recat, prod)) {
				break;
			}

		}

		return balanceCoefs;
	}

	public static void sum3(ArrayList<Molecule> recat, ArrayList<Molecule> prod, int k, ArrayList<Integer> maxCoefs) {

		if (k > balanceCoefs.size()) {
			System.out.println("error soultion not in range- Rais the maxCoef||the equation might not be balencable");
		} else {
			if (k == 0) {
				for (int q = 0; q < maxCoefs.get(k); q++) {

					if (balanceCoefs.get(0) >= maxCoefs.get(k)) {
						balanceCoefs.set(0, 1);
						Main.sum3(recat, prod, k + 1, maxCoefs);
					}
					reCoefs = Main.split(balanceCoefs, recat, true);
					prCoefs = Main.split(balanceCoefs, recat, false);

					if (Main.isEqual(recat, prod)) {
						System.out.println("reached end #2");
						System.out.println(balanceCoefs.subList(0, balanceCoefs.size()));
						break;
					}
					balanceCoefs.set(0, balanceCoefs.get(0) + 1);
					System.out.println("current attempt			" + balanceCoefs.subList(0, balanceCoefs.size()));
				}

			} else {
				balanceCoefs.set(k, balanceCoefs.get(k) + 1);
				if (balanceCoefs.get(k) >= maxCoefs.get(k)) {
					balanceCoefs.set(k, 1);
					Main.sum3(recat, prod, k + 1, maxCoefs);
				}
				System.out.println("current attempt			" + balanceCoefs.subList(0, balanceCoefs.size()));

			}
		}
		// System.out.println(balanceCoefs.subList(0, balanceCoefs.size()));
	}

	public static void sum4(ArrayList<Molecule> recat, ArrayList<Molecule> prod, int k, ArrayList<Integer> maxCoefs) {
//		try {
//			Thread.sleep(500);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		if (k > balanceCoefs.size() - 1) {
			System.out.println("error soultion not in range- Rais the maxCoef||the equation might not be balencable");
		} else {
			if (k == 0) {
//				if (balanceCoefs.get(0) >= maxCoefs.get(k)) {
//					balanceCoefs.set(0, 1);
//					Main.sum4(recat, prod, k + 1, maxCoefs);
//				}
//
//				reCoefs = Main.split(balanceCoefs, recat, true);
//				prCoefs = Main.split(balanceCoefs, recat, false);
//
//				if (Main.isEqual(recat, prod)) {
//					System.out.println("reached end #1");
//					System.out.println(balanceCoefs.subList(0, balanceCoefs.size()));
//					break;
//				}
//				balanceCoefs.set(0, balanceCoefs.get(0) + 1);
//				// -
//				
//				Main.addElements(recat, recat, Main.computCoef(recat, reCoefs), true);
//				Main.addElements(recat, prod, Main.computCoef(prod, prCoefs), false);
//				System.out.println("		this : " + reactentsOld.subList(0, reactentsOld.size()));
////				try {
////					Thread.sleep(4500);
////				} catch (InterruptedException e) {
////					// TODO Auto-generated catch block
////					e.printStackTrace();
////				}
//				if (Main.hasElemGreater(recat, recat.get(k), true)) {
//					balanceCoefs.set(k, 1);
//					Main.sum4(recat, prod, k + 1, maxCoefs);
//					break;
//				}
//				for (int reint = 0; reint < reactents.size(); reint++) {
//					reactentsOld.set(reint, reactents.get(reint));
//				}
//
//				System.out.println("		current attempt" + balanceCoefs.subList(0, balanceCoefs.size()));

				for (int q = 0; q < maxCoefs.get(k); q++) {
					balanceCoefs.set(k,balanceCoefs.get(k)+1);
					reCoefs = Main.split(balanceCoefs, recat, true);
					prCoefs = Main.split(balanceCoefs, recat, false);
	
					if (Main.isEqual(recat, prod)) {
						System.out.println("reached end #1");
						System.out.println(balanceCoefs.subList(0, balanceCoefs.size()));
						break;
					}
					if (balanceCoefs.get(k) >= maxCoefs.get(k)) {
						balanceCoefs.set(k, 1);
						Main.sum4(recat, prod, k + 1, maxCoefs);
					}
					
				}

			} else {

				if (balanceCoefs.get(k) >= maxCoefs.get(k)) {
					balanceCoefs.set(k, 1);
					Main.sum4(recat, prod, k + 1, maxCoefs);
				}

				reCoefs = Main.split(balanceCoefs, recat, true);
				prCoefs = Main.split(balanceCoefs, recat, false);
				if (Main.isEqual(recat, prod)) {
					System.out.println("reached end #2");
					System.out.println(balanceCoefs.subList(0, balanceCoefs.size()));
				} else {
					balanceCoefs.set(k, balanceCoefs.get(k) + 1);
					System.out.println("		this : " + reactentsOld.subList(0, reactentsOld.size()));
//					try {
//						Thread.sleep(4500);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
					if (k <= recat.size() - 1) {

						if (Main.hasElemGreater(recat, recat.get(k), true)) {
							balanceCoefs.set(k, 1);
							Main.sum4(recat, prod, k + 1, maxCoefs);

						}
					} else {
						if (Main.hasElemGreater(recat, prod.get(k - (recat.size())), false)) {

							if (k < balanceCoefs.size() - 1) {
								balanceCoefs.set(k, 1);
								Main.sum4(recat, prod, k + 1, maxCoefs);
							}

						}

					}
				}

				System.out.println("		current attempt" + balanceCoefs.subList(0, balanceCoefs.size()));

			}
		}
	}

	public static boolean hasElemGreater(ArrayList<Molecule> recat, Molecule compound, boolean isReact) {
		
		boolean hasElems = false;
		ArrayList<String> elementList = Main.findElements(recat);
		ArrayList<Molecule> Compound = new ArrayList<>(Arrays.asList(compound));
		ArrayList<String> elementMList = Main.findElements(Compound);
		if (isReact) {
			for (int size = 0; size < elementList.size(); size++) {
				for (int size2 = 0; size2 < elementMList.size(); size2++) {
					if (elementList.get(size).equals(elementMList.get(size2))) {

						if (reactents.get(size) > products.get(size)) {
							hasElems = true;

						}

					}
				}
			}
		} else {
			for (int size = 0; size < elementList.size(); size++) {
				for (int size2 = 0; size2 < elementMList.size(); size2++) {
					if (elementList.get(size).equals(elementMList.get(size2))) {
						System.out.println("sublist reactents : " + reactents.subList(0, reactents.size()));
						System.out.println("sublist products : " + products.subList(0, products.size()));
						if (reactentsOld.get(size) < products.get(size)) {
							hasElems = true;

						}

					}
				}
			}
//			int count = 0;
//			for(int baSize = 0; baSize< balanceCoefs.size()-prCoefs.size();baSize++) {
//				if(balanceCoefs.get(baSize)==1) {
//					count++;
//				}
//			}
//			if(count == balanceCoefs.size()-prCoefs.size()) {
//				hasElems = false;
//			}

		}

		return hasElems;
	}
//works
	public static ArrayList<Integer> split(ArrayList<Integer> toBeSpl, ArrayList<Molecule> recat, boolean front) {
		ArrayList<Integer> part = new ArrayList<Integer>();
		for (int halfish = 0; halfish < toBeSpl.size(); halfish++) {
			if (front) {
				if (halfish <= recat.size() - 1) {
					part.add(toBeSpl.get(halfish));
				}
			} else {
				if (halfish > recat.size() - 1) {
					part.add(toBeSpl.get(halfish));
				}
			}
		}
		return part;
	}

}
