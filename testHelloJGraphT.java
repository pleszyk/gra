/* 
 * Copyright (C) 2018 Karl R. Wurst
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301, USA
 */

package org.jgrapht.dem0;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import org.jgrapht.*;
import org.jgrapht.graph.*;

import java.net.*;

import org.jgrapht.demo.HelloJGraphT;

/**
 * Tests for HelloJGraphT
 * 
 * @author Karl R. Wurst
 * @version CS-348 Spring 2019
 */
public class testHelloJGraphT {

	@Test
	public void testStringGraph() {
	    Graph<String, DefaultEdge> stringGraph = HelloJGraphT.createStringGraph();
	    assertEquals("([v1, v2, v3, v4], [{v1,v2}, {v2,v3}, {v3,v4}, {v4,v1}])",
			 stringGraph.toString());
	}

    	@Test
	public void testHrefGraph() throws URISyntaxException {
	    Graph<URI, DefaultEdge> hrefGraph = HelloJGraphT.createHrefGraph();
	    assertEquals("([http://www.google.com, http://www.wikipedia.org, http://www.jgrapht.org], [(http://www.jgrapht.org,http://www.wikipedia.org), (http://www.google.com,http://www.jgrapht.org), (http://www.google.com,http://www.wikipedia.org), (http://www.wikipedia.org,http://www.google.com)])",
			 hrefGraph.toString());
	}

        @Test
	public void testTraverseHrefGraph() throws URISyntaxException {
	    Graph<URI, DefaultEdge> hrefGraph = HelloJGraphT.createHrefGraph();
	    URI start = hrefGraph.vertexSet().stream().filter(uri -> uri.getHost().equals("www.jgrapht.org")).findAny().get();
	    assertEquals("http://www.jgrapht.org\nhttp://www.wikipedia.org\nhttp://www.google.com",
			 HelloJGraphT.traverseHrefGraph(hrefGraph, start));
	}
}
