package Q1;

public class RedBlackTree<T extends Comparable<T>> {
    public static final int red = 0;
    public static final int black = 1;
    private int __color;
    private T __val;
    private RedBlackTree<T> __left;
    private RedBlackTree<T> __right;

    private RedBlackTree(RedBlackTree<T> b) {
        __val = b.__val;
        __left = b.__left;
        __right = b.__right;
        __color = red;
    }

    private void copy(RedBlackTree<T> x) {
        __val = x.__val;
        __left = x.__left;
        __right = x.__right;
        __color = x.__color;
    }

    private RedBlackTree<T> RBinsertLeft(T k, int sw) {
        RedBlackTree<T> l;
        RedBlackTree<T> b;
        l = __left;
        if (l == null) {
            __left = b = new RedBlackTree<T>(k);
        } else {
            b = l.RBinsert(k, sw);
        }
        return b;
    }

    private RedBlackTree<T> RBinsertRight(T k, int sw) {
        RedBlackTree<T> r;
        RedBlackTree<T> b;
        r = __right;
        if (r == null) {
            __right = b = new RedBlackTree<T>(k);
        } else {
            b = r.RBinsert(k, sw);
        }
        return b;
    }

    private RedBlackTree<T> rotLeft() {
        RedBlackTree<T> x;
        RedBlackTree<T> me;
        if (__right == null) return null;
        me = new RedBlackTree<T>(this);
        x = me.__right;
        me.__right = x.__left;
        x.__left = me;
        return x;
    }

    private RedBlackTree<T> rotRight() {
        RedBlackTree<T> x;
        RedBlackTree<T> me;
        if (__left == null) return null;
        me = new RedBlackTree<T>(this);
        x = me.__left;
        me.__left = x.__right;
        x.__right = me;
        return x;
    }


    private RedBlackTree<T> RBinsert(T k, int sw) {
        RedBlackTree<T> b = null;
        RedBlackTree<T> x;
        RedBlackTree<T> l;
        RedBlackTree<T> ll;
        RedBlackTree<T> r;
        RedBlackTree<T> rr;
        l = __left;
        r = __right;
        if ((l != null) && (l.__color == red) && (r != null) && (r.__color == red)) {
            __color = red;
            l.__color = black;
            r.__color = black;
        }

        if (k.compareTo(__val) < 0) {
            b = RBinsertLeft(k, 0);
            l = __left;
            if ((__color == red) && (l != null) && (l.__color == red) && (sw == 1)) {
                x = rotRight();
                if (x != null) {
                    copy(x);
                }
            }
            l = __left;
            if (l != null) {
                ll = l.__left;
                if (ll != null) {
                    if ((l.__color == red) && (ll.__color == red)) {
                        x = rotRight();
                        if (x != null) {
                            copy(x);
                        }
                        __color = black;
                        r = __right;
                        if (r != null) {
                            r.__color = red;
                        }
                    }
                }
            }
        } else {
            b = RBinsertRight(k, 1);
            r = __right;
            if ((__color == red) && (r != null) && (r.__color == red) && (sw == 0)) {
                x = rotLeft();
                if (x != null) {
                    copy(x);
                }
            }
            r = __right;
            if (r != null) {
                rr = r.__right;
                if (rr != null) {
                    if ((r.__color == red) && (rr.__color == red)) {
                        x = rotLeft();
                        if (x != null) {
                            copy(x);
                        }
                        __color = black;
                        l = __left;
                        if (l != null) {
                            l.__color = red;
                        }
                    }
                }
            }
        }
        return b;
    }


    public RedBlackTree(T x) {
        __val = x;
        __left = null;
        __right = null;
        __color = red;
    }


    public String toString() {
        String col = __color == 0 ? "red" : "black";
        return "[" + __val + ", " + col + "]";
    }

    public T val() {
        return __val;
    }


    public int color() {
        return __color;
    }


    public RedBlackTree<T> find(T key) {
        RedBlackTree<T> result = null;
        if (key == __val) {
            result = this;
        } else if (key.compareTo(__val) < 0) {
            if (__left != null) {
                result = __left.find(key);
            }
        } else {
            if (__right != null) {
                result = __right.find(key);
            }
        }
        return result;

    }

    public void inorder(NodeVisitor visitor, int depth) {
        if (__left != null) {
            __left.inorder(visitor, depth + 1);
        }
        visitor.visit(this, depth);
        if (__right != null) {
            __right.inorder(visitor, depth + 1);
        }
    }

    public RedBlackTree<T> insert(T k) {
        RedBlackTree<T> b;
        b = RBinsert(k, 0);
        __color = black;
        return b;

    }
}