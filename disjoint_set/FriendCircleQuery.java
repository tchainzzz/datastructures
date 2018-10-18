// URL: https://www.hackerrank.com/challenges/friend-circle-queries/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=miscellaneous

package disjoint_set;
import java.util.Vector;
import java.io.File;
import java.util.Scanner;

import java.io.IOException;
import java.util.Comparator;
import java.util.Collections;
import java.util.Iterator;

class UnionFind {
	Vector<Edge> edges;
	// To store the parent nodes of all the nodes
	int[] parent;
	// To store the rank of all the parent nodes; Rank is not equal to height of tree
	// rank(x) <= height(x) where x is the parent node of the tree that is getting formed.
	int[] rank;

	UnionFind() {
		edges = new Vector<Edge>();
		parent = new int[n];
	}

	// Here, need to implement path compression to improve traversal of the parent tree.
	void findParent(int x) {
		if (parent != null && parent[x])
	}
	void addEdge(Vector<Edge> edgelist) {
		edges = edgelist;
	}

	void sortEdgeByWeight() {
		Collections.sort(edges, new Comparator<Edge>() {

			@Override
			public int compare(Edge a, Edge b) {
				if (a.weight < b.weight)
					return -1;
				return 1;
			}

		});
	}

	void printEdge() {
		Iterator<Edge> it = edges.iterator();

		while (it.hasNext()) {
			Edge e = it.next();
			System.out.println(e.x + " " + e.y + " " + e.weight);
		}
	}
}


public class FriendCircleQuery {
	// Get the total number of nodes present in the graph/edgelist.
	// This works provided the edgelist has consecutive node numbers assigned and they are positive.
	public static int totalNodes(Vector<Edge> edgelist) {
		Iterator<Edge> it = edgelist.iterator();

		int totalNodes = -1;
		while (it.hasNext()) {
			Edge edge = it.next();
			if (totalNodes < edge.x) {
				totalNodes = edge.x;
			} 
			else if (totalNodes < edge.y) {
				totalNodes = edge.y;
			}
		}
	}

	public static void main(String[] args) {
		try {
			File f = new File("edgelist");
			Vector<Edge> edgelist = new Vector<Edge>();
			UnionFind ufind = new UnionFind();
			Scanner sc = new Scanner(f);

			while (sc.hasNext()) {
				String split[] = sc.nextLine().split(" ");
				int a = Integer.parseInt(split[0]);
				int b = Integer.parseInt(split[1]);
				int wt = Integer.parseInt(split[2]);
				edgelist.add(new Edge(a, b, wt));
			}

			int totalNodes = totalNodes(edgelist);

			ufind.addEdge(edgelist);
			ufind.sortEdgeByWeight();
			ufind.printEdge();
		}
		catch(IOException e) {
			System.out.println(e);
		}



	}
}