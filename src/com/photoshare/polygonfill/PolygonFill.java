package com.photoshare.polygonfill;

import java.util.ArrayList;

/*
 * ��;����̬ɨ������
 */
class AELvertex {
	double x;
	double y;
	double dx;// б�ʵĵ���

	public void copy() {

	}
};

/*
 * ��;������ζ���
 */
class Polygon {

	boolean isComplete;// ����ֵ��ʾ��ǰ������Ƿ�滭���
	ArrayList<Point> points;// ����εĵ㼯

	Polygon() {
		isComplete = false;
	}
};

public class PolygonFill {
	static int width;
	static int height;
	DrawLineDelegate delegate = null;

	public PolygonFill(int width, int height, DrawLineDelegate d) {
		PolygonFill.width = width;
		PolygonFill.height = height;
		this.delegate = d;
	}

	public PolygonFill(int width, int height) {
		PolygonFill.width = width;
		PolygonFill.height = height;
	}

	void add(ArrayList r1, ArrayList r2) {
		for (int i = 0; i < r2.size(); i++) {
			r1.add(r2.get(i));
		}
	}

	static void swap(AELvertex v1, AELvertex v2) {
		double i;
		i = v1.x;
		v1.x = v2.x;
		v2.x = i;
		i = v1.dx;
		v1.dx = v2.dx;
		v2.dx = i;
		i = v1.y;
		v1.y = v2.y;
		v2.y = i;
	}

	static void sort(ArrayList<AELvertex> rel) {
		for (int i = 0; i < rel.size(); i++) {
			for (int k = 0; k < rel.size() - 1; k++) {
				if (rel.get(k).x > rel.get(k + 1).x) {
					swap(rel.get(k), rel.get(k + 1));
				}
				if (rel.get(k).x == rel.get(k + 1).x) {
					if (rel.get(k).dx > rel.get(k + 1).dx) {
						swap(rel.get(k), rel.get(k + 1));
					}
				}
			}
		}
	}

	public void polygonFill(ArrayList<Point> polygon) {
		ArrayList et[] = new ArrayList[height];
		ArrayList<AELvertex> p;
		AELvertex q;
		for (int j = 0; j < height; j++)
			et[j] = null;
		int k = 0;

		for (int i = 0; i < polygon.size(); i++) {
			p = new ArrayList<AELvertex>();
			q = new AELvertex();
			if (polygon.get(i).y < polygon.get((i + 1) % polygon.size()).y) {
				k = polygon.get(i).y;
				q.x = polygon.get(i).x;
				q.y = polygon.get((i + 1) % polygon.size()).y;
				q.dx = (polygon.get(i).x * 1.0 - polygon.get((i + 1)
						% polygon.size()).x * 1.0)
						/ (polygon.get(i).y * 1.0 - polygon.get((i + 1)
								% polygon.size()).y * 1.0);
			}
			if (polygon.get(i).y > polygon.get((i + 1) % polygon.size()).y) {
				k = polygon.get((i + 1) % polygon.size()).y;
				q.x = polygon.get((i + 1) % polygon.size()).x;
				q.y = polygon.get(i).y;
				q.dx = (polygon.get(i).x * 1.0 - polygon.get((i + 1)
						% polygon.size()).x * 1.0)
						/ (polygon.get(i).y * 1.0 - polygon.get((i + 1)
								% polygon.size()).y * 1.0);
			}

			if (et[k] == null) {
				p.add(q);
				et[k] = p;
			} else {
				et[k].add(q);
			}
		}

		ArrayList<AELvertex> AEL = new ArrayList<AELvertex>();
		AELvertex startAEL = new AELvertex();
		startAEL.x = 0;
		startAEL.dx = 0;
		startAEL.y = height;
		AELvertex endAEL = new AELvertex();
		endAEL.x = width;
		endAEL.dx = 0;
		endAEL.y = height;
		AEL.add(startAEL);
		AEL.add(endAEL);
		for (int i = 0; i < height; i++) {
			if (et[i] != null) {

				add(AEL, et[i]);
				et[i] = null;
				sort(AEL);

			}

			if (AEL.size() > 0) {
				for (int j = 0; j < AEL.size(); j++) {
					k = (int) AEL.get(j).y;
					if (k <= i) {

						AEL.remove(j);
						j--;

					}
				}
				AELvertex temp1 = null;
				AELvertex temp2 = null;
				for (int j = 0; j < AEL.size() - 1; j += 2) {
					temp1 = AEL.get(j);
					temp2 = AEL.get(j + 1);
					int a, b;
					a = (int) (temp1.x + 0.5);
					b = (int) (temp2.x + 0.5);
					delegate.line(a, i, b, i);
					temp1.x = temp1.x + temp1.dx;
					temp2.x = temp2.x + temp2.dx;
				}

			}

		}
	}

}
