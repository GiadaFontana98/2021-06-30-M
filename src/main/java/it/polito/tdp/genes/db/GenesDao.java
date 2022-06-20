package it.polito.tdp.genes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.genes.model.Genes;
import it.polito.tdp.genes.model.Interactions;


public class GenesDao {
	
	public List<Genes> getAllGenes(){
		String sql = "SELECT DISTINCT GeneID, Essential, Chromosome FROM Genes";
		List<Genes> result = new ArrayList<Genes>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Genes genes = new Genes(res.getString("GeneID"), 
						res.getString("Essential"), 
						res.getInt("Chromosome"));
				result.add(genes);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
	}
	
	public List<Integer> getAllCromosomi(){
		String sql = "SELECT  DISTINCT Chromosome "
				+ "FROM genes "
				+ "WHERE Chromosome <> 0 ";
		List<Integer> result = new ArrayList<Integer>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				
				result.add(res.getInt("Chromosome"));
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
	}
	
	public int getPeso(int c1, int c2){
		String sql = "SELECT SUM(Expression_Corr) AS peso "
				+ "FROM "
				+ "	(SELECT g1.Chromosome AS c1 , g2.Chromosome AS c2, i.GeneID1 AS g1, i.GeneID2 AS g2, i.Expression_Corr "
				+ "	FROM  interactions i ,genes g1 , genes g2 "
				+ "	WHERE i.GeneID1 = g1.GeneID AND i.GeneID2 = g2.GeneID "
				+ "	 		AND g1.Chromosome=?  AND g2.Chromosome = ? "
				+ "	GROUP BY g1.GeneID , g2.GeneID) AS T ";
		int p=0;
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, c1);
			st.setInt(2, c2);
			ResultSet res = st.executeQuery();
		
			while (res.next()) {

				
				p=res.getInt("peso");
			}
			res.close();
			st.close();
			conn.close();
			return p;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
	}
	
	
	


	
}
