import java.util.*;
public class Main {
	static Scanner leia = new Scanner(System.in);
	
	public static void main(String[] args) {	
		Carro carro = new Carro();
    	byte opcao = -1;
    	 
    	do {
			do {
    			System.out.println("\n ***************  CADASTRO DE CARROS  ***************** ");
    			System.out.println(" [1] INCLUIR CARROS ");
    			System.out.println(" [2] ALTERAR CARROS ");
    			System.out.println(" [3] CONSULTAR CARROS ");
    			System.out.println(" [4] EXCLUIR CARROS ");
    			System.out.println(" [0] SAIR");
    			System.out.print("\nDigite a opcao desejada: ");
    			opcao = leia.nextByte();
    			if (opcao < 0 || opcao > 4) {
    				System.out.println("opcao Invalida, digite novamente.\n");
    			}
    		}while (opcao < 0 || opcao > 4);
			
			switch (opcao) {
				case 0:
					System.out.println("\n ************  PROGRAMA ENCERRADO  ************** \n");
					break;
				case 1: 
					carro.incluir(); 
					break;
				case 2:
					carro.alterar();
					break;
				
				case 3: 
					carro.consultar();
					break;
				
				case 4: 
					carro.excluir();
					break;
					
			}
    	} while ( opcao != 0 );
    	leia.close();
	}

}