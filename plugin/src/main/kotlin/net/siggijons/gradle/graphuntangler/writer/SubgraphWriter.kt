package net.siggijons.gradle.graphuntangler.writer

import net.siggijons.gradle.graphuntangler.color.ColorMode
import net.siggijons.gradle.graphuntangler.graph.AffectedSubgraphDetails
import net.siggijons.gradle.graphuntangler.graph.IsolatedSubgraphDetails
import net.siggijons.gradle.graphuntangler.graph.SubgraphDetails
import java.io.File

class SubgraphWriter(
    private val projectsDir: File
) {
    private val graphvizWriter = GraphvizWriter()

    fun write(
        subgraphs: List<SubgraphDetails>,
        isolatedSubgraphs: List<IsolatedSubgraphDetails>,
        affectedSubgraphs: List<AffectedSubgraphDetails>
    ) {
        writeProjectSubgraphs(subgraphs, projectsDir)
        writeIsolatedSubgraphs(isolatedSubgraphs, projectsDir)
        writeAffectedSubgraphs(affectedSubgraphs, projectsDir)
    }

    private fun writeProjectSubgraphs(
        graphs: List<SubgraphDetails>,
        outputDir: File
    ) {
        graphs.forEach { subgraph ->
            with(subgraph) {
                graphvizWriter.writeDotGraph(
                    subgraph.subgraph,
                    File(outputDir, "${vertex.safeFileName}.gv")
                )

                graphvizWriter.writeDotGraph(
                    subgraphHeightGraph,
                    File(outputDir, "${vertex.safeFileName}-height.gv")
                )
            }
        }
    }

    private fun writeIsolatedSubgraphs(
        graphs: List<IsolatedSubgraphDetails>,
        outputDir: File
    ) {
        graphs.forEach { details ->
            graphvizWriter.writeDotGraph(
                graph = details.isolatedDag,
                file = File(outputDir, "${details.vertex.safeFileName}-isolated.gv"),
                colorMode = ColorMode.OWNER
            )

            graphvizWriter.writeDotGraph(
                graph = details.reducedDag,
                file = File(outputDir, "${details.vertex.safeFileName}-isolated-reduced.gv"),
                colorMode = ColorMode.OWNER
            )
        }
    }

    private fun writeAffectedSubgraphs(
        graphs: List<AffectedSubgraphDetails>,
        outputDir: File
    ) {
        graphs.forEach { details ->
            graphvizWriter.writeDotGraph(
                graph = details.affectedDag,
                file = File(outputDir, "${details.vertex.safeFileName}-affected.gv"),
                colorMode = ColorMode.OWNER
            )

            graphvizWriter.writeDotGraph(
                graph = details.reducedDag,
                file = File(outputDir, "${details.vertex.safeFileName}-affected-reduced.gv"),
                colorMode = ColorMode.OWNER
            )
        }
    }
}
