package swmm.datastructure

/**
 * Created by ning on 6/7/15.
 */
 class MTree[T](node: T, children: List[MTree[T]]) {
  def this(node: T) = this(node, List())
  override def toString = "M(" + node.toString + " {" + children.map(_.toString).mkString(",") + "})"
}

object MTree {
  def apply[T](value: T) = new MTree(value, List())
  def apply[T](value: T, children: List[MTree[T]]) = new MTree(value, children)
}