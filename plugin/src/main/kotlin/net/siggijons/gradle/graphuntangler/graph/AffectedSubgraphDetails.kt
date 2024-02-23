package net.siggijons.gradle.graphuntangler.graph

import org.jgrapht.graph.AbstractGraph

data class AffectedSubgraphDetails(
    val vertex: DependencyNode,
    val affectedDag: AbstractGraph<DependencyNode, DependencyEdge>,
    val reducedDag: AbstractGraph<DependencyNode, DependencyEdge>,
    val affectedDagSize: Int,
    val fullGraphSize: Int
)
