package it.polito.tdp.genes.model;

import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.genes.db.GenesDao;

public class Model {
	Graph<Integer,DefaultWeightedEdge>grafo;
	GenesDao dao;
	
	public Model() {
		dao = new GenesDao();
	}
	
	public String creaGrafo()
	{
		grafo= new SimpleDirectedWeightedGraph<Integer,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		List<Integer>vertici= dao.getAllCromosomi();
		Graphs.addAllVertices(grafo,vertici);
		int c = 0;
		for(Integer i : vertici)
		{
			for(Integer j : vertici)
			{
				int peso=dao.getPeso(i, j);
				if(peso != 0 &&i!=j)
				{
					Graphs.addEdgeWithVertices(grafo, i,j,peso);
					System.out.println(c++ + "\n");
				}
					
			}
		}
		
		c= 0;
		for(Integer i : vertici)
		{
			for(Integer j : vertici)
			{
				int peso=dao.getPeso(i, j);
				if(peso != 0 && i!=j)
				{
					Graphs.addEdgeWithVertices(grafo, j, i,peso);
					System.out.println(c++ + "\n");
				}
					
			}
		}
		
		
		return "#Vertici " + grafo.vertexSet().size() + " #Archi " + grafo.edgeSet().size();
		
		
	}

}