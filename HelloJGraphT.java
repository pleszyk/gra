/*
 * (C) Copyright 2003-2018, by Barak Naveh and Contributors.
 *
 * JGraphT : a free Java graph-theory library
 *
 * See the CONTRIBUTORS.md file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the
 * GNU Lesser General Public License v2.1 or later
 * which is available at
 * http://www.gnu.org/licenses/old-licenses/lgpl-2.1-standalone.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR LGPL-2.1-or-later
 */

/* Modified by Karl R. Wurst (karl@w-sts.com) for use in a course
   Original version at https://jgrapht.org/guide/HelloJGraphT

   Made some private methods public so that they can be called by tests.
   Modified traverseHrefGraph to return a String instead of printing, so it can be used in a test.
   Modified main to use returned String instead of printing by side effect.
*/
   
package org.jgrapht.demo;

import org.jgrapht.*;
import org.jgrapht.graph.*;
import org.jgrapht.io.*;
import org.jgrapht.traverse.*;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * A simple introduction to using JGraphT.
 *
 * @author Barak Naveh
 */
public final class HelloJGraphT
{
    private HelloJGraphT()
    {
    } // ensure non-instantiability.

    /**
     * The starting point for the demo.
     *
     * @param args ignored.
     *
     * @throws URISyntaxException if invalid URI is constructed.
     * @throws ExportException if graph cannot be exported.
     */
    public static void main(String[] args)
        throws URISyntaxException,
        ExportException
    {
        Graph<String, DefaultEdge> stringGraph = createStringGraph();

        // note undirected edges are printed as: {<v1>,<v2>}
        System.out.println("-- toString output");
        System.out.println(stringGraph.toString());
        System.out.println();


        // create a graph based on URI objects
        Graph<URI, DefaultEdge> hrefGraph = createHrefGraph();

        // find the vertex corresponding to www.jgrapht.org
        URI start = hrefGraph
            .vertexSet().stream().filter(uri -> uri.getHost().equals("www.jgrapht.org")).findAny()
            .get();


        // perform a graph traversal starting from that vertex
        System.out.println("-- traverseHrefGraph output");
        System.out.println(traverseHrefGraph(hrefGraph, start));
	System.out.println();

        System.out.println("-- renderHrefGraph output");
        renderHrefGraph(hrefGraph);
        System.out.println();
    }

    /**
     * Creates a toy directed graph based on URI objects that represents link structure.
     *
     * @return a graph based on URI objects.
     */
    public static Graph<URI, DefaultEdge> createHrefGraph()
        throws URISyntaxException
    {

        Graph<URI, DefaultEdge> g = new DefaultDirectedGraph<>(DefaultEdge.class);

        URI google = new URI("http://www.google.com");
        URI wikipedia = new URI("http://www.wikipedia.org");
        URI jgrapht = new URI("http://www.jgrapht.org");

        // add the vertices
        g.addVertex(google);
        g.addVertex(wikipedia);
        g.addVertex(jgrapht);

        // add edges to create linking structure
        g.addEdge(jgrapht, wikipedia);
        g.addEdge(google, jgrapht);
        g.addEdge(google, wikipedia);
        g.addEdge(wikipedia, google);


        return g;
    }

    /**
     * Traverse a graph in depth-first order and print the vertices.
     *
     * @param hrefGraph a graph based on URI objects
     *
     * @param start the vertex where the traversal should start
     */
    public static String traverseHrefGraph(Graph<URI, DefaultEdge> hrefGraph, URI start)
    {
	String traversal = "";
        Iterator<URI> iterator = new DepthFirstIterator<>(hrefGraph, start);
        while (iterator.hasNext()) {
            URI uri = iterator.next();
            traversal += uri + "\n";
        }
	return traversal.substring(0, traversal.length() - 1);
    }

    /**
     * Render a graph in DOT format.
     *
     * @param hrefGraph a graph based on URI objects
     */
    private static void renderHrefGraph(Graph<URI, DefaultEdge> hrefGraph)
        throws ExportException
    {

        // use helper classes to define how vertices should be rendered,
        // adhering to the DOT language restrictions
        ComponentNameProvider<URI> vertexIdProvider = new ComponentNameProvider<URI>()
        {
            public String getName(URI uri)
            {
                return uri.getHost().replace('.', '_');
            }
        };
        ComponentNameProvider<URI> vertexLabelProvider = new ComponentNameProvider<URI>()
        {
            public String getName(URI uri)
            {
                return uri.toString();
            }
        };
        GraphExporter<URI, DefaultEdge> exporter =
            new DOTExporter<>(vertexIdProvider, vertexLabelProvider, null);
        Writer writer = new StringWriter();
        exporter.exportGraph(hrefGraph, writer);
        System.out.println(writer.toString());
    }

    /**
     * Create a toy graph based on String objects.
     *
     * @return a graph based on String objects.
     */
    public static Graph<String, DefaultEdge> createStringGraph()
    {
        Graph<String, DefaultEdge> g = new SimpleGraph<>(DefaultEdge.class);

        String v1 = "v1";
        String v2 = "v2";
        String v3 = "v3";
        String v4 = "v4";

        // add the vertices
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);

        // add edges to create a circuit
        g.addEdge(v1, v2);
        g.addEdge(v2, v3);
        g.addEdge(v3, v4);
        g.addEdge(v4, v1);

        return g;
    }
}
