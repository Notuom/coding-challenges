package day7

import java.io.File

fun main() {
    val nodeMap = mutableMapOf<String, BagNode>()
    File("day7.txt").forEachLine { line ->
        Regex("""(.*?)\s+bags\s+contain\s+(.*?)\.""").find(line)?.groupValues?.let { bagContents ->
            val parentColor = bagContents[1]
            val parentNode = nodeMap.getOrPut(parentColor) { BagNode(parentColor) }
            bagContents[2].split(", ").forEach { bagListString ->
                Regex("""(\d+)\s+(.*?)\s+bags?""").find(bagListString)?.groupValues?.let { bagListContents ->
                    val amount = bagListContents[1].toInt()
                    val childColor = bagListContents[2]

                    val childNode = nodeMap.getOrPut(childColor) { BagNode(childColor) }
                    parentNode.lineItems += BagLineItem(childNode, amount)
                }
            }
        }
    }

    val shinyGoldTree = nodeMap["shiny gold"]!!

    // get total count of bags and remove 1 for the topmost bag
    println(shinyGoldTree.getTotalCount() - 1)
}
