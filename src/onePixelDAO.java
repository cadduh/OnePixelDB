import java.sql.*;
public class onePixelDAO {
	
	public pixelGetSet pixel;
	public connectionDB bd;
	public PreparedStatement statement;
	public ResultSet resultado;
	
    public static final byte INCLUSAO = 1;
    public static final byte ALTERACAO = 2;
    public static final byte EXCLUSAO = 3;
    
    private String sql = "",men;
    
    
    public onePixelDAO() {
		pixel = new pixelGetSet();
		bd = new connectionDB();
	}
    
    public boolean buscar() {
    	//select * from user INNER JOIN inventario on inventario.user_id = user.user_id WHERE user.user_id = 1 ;
    	sql = "SELECT * FROM user INNER JOIN inventario on inventario.user_id = user.user_id WHERE user.user_id = ?";
    	try {
    		statement = bd.c.prepareStatement(sql);
    		statement.setString(1,pixel.getId());
    		resultado = statement.executeQuery();
    		resultado.next();
    		
    		//Colocando o resultado da busca no get e set
    		
    		pixel.setId(resultado.getString(1));
    		pixel.setName(resultado.getString(2));
    		pixel.setGenero(resultado.getString(3));
    		pixel.setCheckpoint(resultado.getString(4));
    		pixel.setPixelR(resultado.getInt(6));
    		pixel.setPixelG(resultado.getInt(7));
    		pixel.setPixelB(resultado.getInt(8));
     		pixel.setAliado1(resultado.getInt(9));
    		pixel.setAliado2(resultado.getInt(10));

    		System.out.println("retorno");
    		return true;
		} catch (SQLException erro) {
			System.out.println("ERRO");
			return false;
		}
    } 
    
    
    public String atualizar(int operacao) {
    	men = "Opera��o realizada com sucesso ";
    	try {
    		System.out.println("Entrou no TRY");
    		if(operacao == INCLUSAO) {
    		   sql = "INSERT INTO user(user_name,user_genero,checkpoint) values(?,?,?)";
   			   statement = bd.c.prepareStatement(sql);
    		   statement.setString(1, pixel.getName());
    		   statement.setString(2, pixel.getGenero());
    		   statement.setString(3, pixel.getCheckpoint());
               System.out.println("Inclus�o user"+statement);  
               
    		}else if(operacao == ALTERACAO) {
    			sql = "UPDATE user set user_name = ?, user_genero = ?, checkpoint = ? WHERE user_id = ? "; //, aliado1 = ? , aliado2 = ?
    			statement = bd.c.prepareStatement(sql);
     		    statement.setString(1, pixel.getName());
     		    statement.setString(2, pixel.getGenero());
     		    statement.setString(3, pixel.getCheckpoint());
     		    statement.setString(4, pixel.getId());
     		    System.out.println("Altera��o user"+statement);  
    		}else if(operacao == EXCLUSAO) {
    			sql = "DELETE from user WHERE user_id  = ?";
    			statement = bd.c.prepareStatement(sql);
    			statement.setString(1,pixel.getId());
    		}
    		if (statement.executeUpdate() == 0) {
				men = "Falha na opera��o";
			}
		} catch (SQLException erro) {
			men = "Falha na opera��o " + erro.toString();
		}
        if(operacao == INCLUSAO) {
 		   atualizarInventario(INCLUSAO);
        }
    	System.out.println(operacao);
    	return men;
    		
    }
    
    public String atualizarInventario(int operacao) {
    	men = "Opera��o realizada com sucesso (Invent�rio)";
    	try {
    		if(operacao == INCLUSAO) {
    			sql = "INSERT INTO inventario(user_id,pixel_R,pixel_G,pixel_B,aliado1,aliado2) values((SELECT max(user_id) from user),?,?,?,?,?)";
    			statement = bd.c.prepareStatement(sql);
//    			statement.setString(1,pixel.getId());
    			statement.setInt(1,pixel.getPixelR());
    			statement.setInt(2,pixel.getPixelG());
    			statement.setInt(3,pixel.getPixelB());
    			statement.setInt(4,pixel.getAliado1());
    			statement.setInt(5,pixel.getAliado2());
    			System.out.println("Inclus�o inventario :"+statement);
    					    			
    		}else if(operacao == ALTERACAO) {
    			sql = "UPDATE inventario set pixel_R = ?,pixel_G = ?,pixel_B = ?,aliado1 = ?,aliado2 = ? WHERE user_id = ? ";
    			statement = bd.c.prepareStatement(sql);
    			statement.setInt(1,pixel.getPixelR());
    			statement.setInt(2,pixel.getPixelG());
    			statement.setInt(3,pixel.getPixelB());
    			statement.setInt(4,pixel.getAliado1());
    			statement.setInt(5,pixel.getAliado2());
    			statement.setString(6,pixel.getId());
    			System.out.println("Altera��o inventario:"+statement);
    		}else if(operacao == EXCLUSAO) {
    			sql = "DELETE from inventario WHERE user_id = ?";
    			statement = bd.c.prepareStatement(sql);
    			statement.setString(1,pixel.getId());
    		}
    		if (statement.executeUpdate() == 0) {
				men = "Falha na opera��o";
			}		
		} catch (SQLException erro) {
			// TODO: handle exception
		}
    	return men;
		
	}
    
}
