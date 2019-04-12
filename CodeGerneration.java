import java.util.ArrayList;

public class CodeGerneration {

	private ArrayList<Node> tree = new ArrayList<>();
	private ArrayList<String> var = new ArrayList<>();

	private String str = null;
	private String acc = null;

	public CodeGerneration(ArrayList<Node> tree) {
		super();
		this.tree = tree;
		inOrderTravers(tree.get(0));
		for (int i = 0; i < var.size(); i++) {
			System.out.println(var.get(i) + " RESW 1");
		}
	}

	public Node inOrderTravers(Node node) {
//		System.out.println(node.getValue());
		if (node.getToken() == 60) {
			System.out.println("Start "+ tree.get(node.getChildren().get(0)).getValue());
		}
		
		if (node.getToken() == 2) {
			Node idList = tree.get(tree.get(0).getChildren().get(3));
			idList.setVisted(true);
			for(int j = 0 ; j < idList.getChildren().size() ;j++) {
				Node varValue = tree.get(idList.getChildren().get(j));
				varValue.setVisted(true);
				var.add(varValue.getValue());
			}
		}
		if (!node.getVisted()) {
			for (int i = 0; i < node.getChildren().size(); i++) {

				int index = node.getChildren().get(i);
				Node n = inOrderTravers(tree.get(index));

				if (node.getToken() == 90 && !node.getChildren().isEmpty()) {
					str = tree.get(node.getChildren().get(0)).getValue();
				}
				if (node.getToken() == 80 && !node.getChildren().isEmpty() && !node.getVisted()) {
					node.setVisted(true);
					Node factorNode = tree.get(node.getChildren().get(0));
					if (factorNode.getToken() == 70) {
						if (acc == null) {
							acc = tree.get(factorNode.getChildren().get(0)).getValue();
							System.out.println(" Load in acc " + tree.get(factorNode.getChildren().get(0)).getValue());
						} else {
							if (tree.get(node.getParentIndex()).getChildren().size() >= 2) {

								Node operationNode = tree.get(tree.get(node.getParentIndex()).getChildren().get(1));

								if (operationNode.getToken() == 13) {
									System.out.println(
											" add in acc " + tree.get(factorNode.getChildren().get(0)).getValue());
								} else {
									System.out.println(
											" mul in acc " + tree.get(factorNode.getChildren().get(0)).getValue());
								}
							}
						}
					}
				}

			} 
		}
		if (str != null && node.getToken() == 90) {
			System.out.println("store " + str);
			System.out.println("");
			acc = null;
			str = null;
		}

		return node;
	}

}
