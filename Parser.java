import java.util.ArrayList;
import java.util.Iterator;

import javafx.util.Pair;

public class Parser {

	private int parentIndex = -1;
	private int index = 0;
	private ArrayList<Pair<String, Integer>> tokens;
	private Node node;
	private ArrayList<Node> tree = new ArrayList<>();

	private static int PROGRAM = 1;
	// private static int FOR = 6;
	// private static int DO = 10;
	// private static int TO = 9;
	// private static int END = 4;
	private static int END_DOT = 5;
	private static int READ = 7;
	private static int WRITE = 8;
	private static int LEFT_BRKT = 15;
	private static int RIGHT_BRKT = 16;
	private static int VAR = 2;
	private static int BEGIN = 3;
	private static int SEMI_COLON = 11;
	private static int EQU = 12;
	private static int PLUS = 13;
	private static int MUL = 18;
	private static int ID = 17;
	private static int COMA = 0;

	public Parser(ArrayList<Pair<String, Integer>> tokens) {
		super();
		this.tokens = tokens;
		node = new Node(parentIndex, 1, "program");
		tree.add(node);
		checkProgram();
	}

	public Pair<String, Integer> getToken(int index) {

		if (index <= tokens.size()) {
			return tokens.get(index);
		} else {
			printError("error: must end by END.");
		}
		return null;
	}

	private void printError(String msg) {
		System.out.println(msg);
		System.exit(0);
	}

	public void checkProgram() {
		Pair<String, Integer> token = getToken(index);
		if (token.getValue() == PROGRAM) {
			addNode(node, 1, "program");
			checkProgName(node, index);
			checkVar(node, index);
			return;

		} else {
			printError("error expect program");
		}
	}

	private Node addNode(Node node, int token, String value) {
		int parentIndex = tree.indexOf(node);
		Node newNode = new Node(parentIndex, token, value);
		int childIndex = tree.size();
		tree.add(newNode);
		node.addChild(childIndex);
		return newNode;

	}

	private void checkProgName(Node node, int index) {
		index++;
		Pair<String, Integer> token = getToken(index);
		if (token.getValue() == ID) {
			Node newNode = addNode(node, 60, "progName");
			addNode(newNode, ID, token.getKey());
			this.index++;
			return;
		} else {
			printError("error expect id");
		}
	}

	private void checkVar(Node node, int index) {
		index++;
		Pair<String, Integer> token;
		token = getToken(index);
		if (token.getValue() == VAR) {
			addNode(node, VAR, "var");
			checkIdList(node, index);
			checkBegin(node);
			return;

		} else {
			printError("error: expect var");
		}
	}

	private void checkIdList(Node node, int index) {
		index++;
		int nextIndex = index + 1;
		Pair<String, Integer> token, firstToken;
		firstToken = getToken(index);
		token = getToken(nextIndex);

		if (firstToken.getValue() == ID) {
			Node newNode = addNode(node, 0, "id-list");
			while (firstToken.getValue() == ID && token.getValue() == COMA) {
				addNode(newNode, ID,  firstToken.getKey());
				index += 2;
				nextIndex = index + 1;
				firstToken = getToken(index);
				token = getToken(nextIndex);
			}
			token = getToken(index);
			index++;
			this.index = index;
			if (token.getValue() == ID) {
				addNode(newNode, ID, token.getKey());
			} else {
				 printError("error: expect id");
			}

		} else {
			printError("error: expect id");
		}

	}

	private void checkBegin(Node node) {
		Pair<String, Integer> token;
		token = getToken(index);
		if (token.getValue() == BEGIN) {
			addNode(node, BEGIN, "BEGIN");
			index++;
			checkStatmentList(node);
			checkEnd();
			return;
		} else {
			printError("error: expect begin");
		}
	}

	private void checkStatmentList(Node node) {
		Node newNode = addNode(node, 0, "stmtList");
		checkstmt(newNode);
		checkListDash(newNode);
	}

	private void checkListDash(Node node) {
		Pair<String, Integer> token;
		token = getToken(index);
		if (token.getValue() != END_DOT) {
			if (token.getValue() == SEMI_COLON) {
				index++;
			}
			// index++;
			Node newNode = addNode(node, 0, "listDash");
			checkstmt(newNode);
			checkListDash(newNode);
		} else {
			// nothing
			return;
		}
	}

	private void checkstmt(Node node) {
		Pair<String, Integer> token;
		token = getToken(index);
		index++;
		if (token.getValue() == READ) {
			Node newNode = addNode(node, READ, "Read");
			checkRead(newNode);

		} else if (token.getValue() == WRITE) {
			Node newNode = addNode(node, WRITE, "write");
			checkWrite(newNode);

		} else if (token.getValue() == ID) {
			Node newNode = addNode(node, 90, "assign");
			addNode(newNode, ID, token.getKey());
			checkAssign(newNode);
		}  else {
			printError("error: expect stmt");
		}
	}

	private void checkRead(Node node) {
		Pair<String, Integer> token;
		token = getToken(index);
		if (token.getValue() == LEFT_BRKT) {
			addNode(node, LEFT_BRKT, "(");
			checkIdList(node, index);
			token = getToken(index);
			if (token.getValue() == RIGHT_BRKT) {
				index++;
				addNode(node, RIGHT_BRKT, ")");
			} else {
				printError("error: excpect )");
			}
		} else {
			printError("error: excpect (");
		}
	}

	private void checkAssign(Node node) {
		Pair<String, Integer> token;
		token = getToken(index);
		if (token.getValue() == EQU) {
			index++;
			addNode(node, EQU, ":=");
			Node newNode = addNode(node, 80, "exp");
			checkExp(newNode);

		} else {
			printError("error: excpect =:");
		}
	}

	private void checkExp(Node node) {
		Pair<String, Integer> token;
		token = getToken(index);
		if (token.getValue() == ID) {
			Node newNode = addNode(node, 70, "factor");
			addNode(newNode, 0, token.getKey());
			index++;
			token = getToken(index);
			if (token.getValue() == PLUS) {
				index++;
				addNode(node, PLUS, "+");
				newNode = addNode(node, 80, "exp");
				checkExp(newNode);

			} else if (token.getValue() == MUL) {
				index++;
				addNode(node, MUL, "*");
				newNode = addNode(node, 80, "exp");
				checkExp(newNode);
			} else {
				// noting
				return;

			}
		} else {
			printError("error: excpect id ");
		}

	}

	private void checkWrite(Node node) {
		Pair<String, Integer> token;
		token = getToken(index);
		if (token.getValue() == LEFT_BRKT) {
			addNode(node, LEFT_BRKT, "(");
			checkIdList(node, index);
			token = getToken(index);
			if (token.getValue() == RIGHT_BRKT) {
				index++;
				addNode(node, RIGHT_BRKT, ")");
			} else {
				printError("error: excpect )");
			}
		} else {
			printError("error: excpect (");
		}
	}

	private void checkEnd() {
		Pair<String, Integer> token;
		token = getToken(index);
		if (token.getValue() == END_DOT) {
//			for (int i = 0; i < tree.size(); i++) {
//				System.out.println(i + "-" + tree.get(i).getValue() + "-" + tree.get(i).getParentIndex() + "-");
//			}
			CodeGerneration code = new CodeGerneration(tree);
			return;
		} else {
			printError("error: expect End.");
		}
	}
}
