package day7

data class BagLineItem(val bag: BagNode, val quantity: Int)

class BagNode(val color: String) {
    private var totalCountMemo: Int? = null
    val lineItems = mutableListOf<BagLineItem>()

    fun containsColor(colorToFind: String, isRoot: Boolean): Boolean {
        if (!isRoot && color == colorToFind) return true
        return lineItems.any { child -> child.bag.containsColor(colorToFind, false) }
    }

    fun print(tabSize: Int = 0, quantity: Int = 0) {
        println(" ".repeat(tabSize) + "[$quantity] " + (if (tabSize == 0) "* $color" else "- $color") + " (${lineItems.size} children)")
        lineItems.forEach { lineItem -> lineItem.bag.print(tabSize + 2, lineItem.quantity) }
    }

    fun getTotalCount(): Int {
        if (totalCountMemo == null) {
            if (lineItems.isEmpty()) {
                totalCountMemo = 1
            } else {
                totalCountMemo = 1 + lineItems.map { lineItem -> lineItem.bag.getTotalCount() * lineItem.quantity }
                    .reduce { acc, subtotal -> acc + subtotal }
            }
        }

        return totalCountMemo!!
    }

    override fun toString(): String {
        return "{ \"color\": \"$color\", \"children\": $lineItems }"
    }
}
