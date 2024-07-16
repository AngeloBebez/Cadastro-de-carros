import java.io.*;

/*
Nomes dos Alunos:
Moisés Felipe Gonçalves
Victor Magnus Martins Grissel
Miguel Angelo dos Santos Evangelista
Henrique Candelori Mendes
*/

//Váriaveis Globais
public class Carro {
	char 	ativo;
	String	codCarro;
	String 	marca;
	String 	modelo;
	float 	motorizacao;
	String  fabricacao;
	String origemMarca;
	String Categoria;
	int potencia;
	float peso;
	float preco;
	String mesAnoFab;

	//Vetor Global
	static String marcas[] = {"TOYOTA","HONDA","VOLKSWAGEN","CHEVROLET","FIAT","HYUNDAI","BMW","MERCEDES BENS","RENAULT","JEEP"};
	static String origens[] = {"JAPAO","JAPAO","ALEMANHA","EUA","ITALIA","COREIA DO SUL","ALEMANHA","ALEMANHA","FRANÇA","EUA"};

	//Método para localizar um registro no disco
	public long pesquisarCarro(String ParametroCarro) {	
		long posicaoCursorArquivo = 0;
		try { 
			RandomAccessFile arqCarro = new RandomAccessFile("CARROS.DAT", "rw");
			while (true) {
				posicaoCursorArquivo = arqCarro.getFilePointer();
				ativo = arqCarro.readChar();
				codCarro = arqCarro.readUTF();
				marca = arqCarro.readUTF();
				modelo = arqCarro.readUTF();
				motorizacao = arqCarro.readFloat();
				fabricacao = arqCarro.readUTF();
				origemMarca = arqCarro.readUTF();
				Categoria = arqCarro.readUTF();
				potencia = arqCarro.readInt();
				peso = arqCarro.readFloat();
				preco = arqCarro.readFloat();
				mesAnoFab = arqCarro.readUTF();

				if ( ParametroCarro.equals(codCarro) && ativo == 'S') {
					arqCarro.close();
					return posicaoCursorArquivo;
				}
			}
		}catch (EOFException e) {
			return -1; // registro nao foi encontrado
		}catch (IOException e) { 
			System.out.println("Erro na abertura do arquivo  -  programa sera finalizado");
			System.exit(0);
			return -1;
		}
	}

	//M[etodo para adicionar um novo registro no final do arquivo em disco
	public void salvarCarro() {	
		try {
			RandomAccessFile arqCarro = new RandomAccessFile("CARROS.DAT", "rw");	
			arqCarro.seek(arqCarro.length());  // posiciona o ponteiro no final do arquivo (EOF)
			arqCarro.writeChar(ativo);
			arqCarro.writeUTF(codCarro);
			arqCarro.writeUTF(marca);
			arqCarro.writeUTF(modelo);
			arqCarro.writeFloat(motorizacao);
			arqCarro.writeUTF(fabricacao);	
			arqCarro.writeUTF(origemMarca);
			arqCarro.writeUTF(Categoria);
			arqCarro.writeInt(potencia);
			arqCarro.writeFloat(peso);
			arqCarro.writeFloat(preco);
			arqCarro.writeUTF(mesAnoFab);
			arqCarro.close();
			System.out.println("Dados gravados com sucesso !\n");
		}catch (IOException e) { 
			System.out.println("Erro na abertura do arquivo  -  programa sera finalizado");
			System.exit(0);
		}
	}

	//Método para alterar o valor do campo ATIVO para N, tornando assim o registro excluido
	public void desativarCarro(long posicao)	{    
		try {
			RandomAccessFile arqCarro = new RandomAccessFile("CARROS.DAT", "rw");			
			arqCarro.seek(posicao);
			arqCarro.writeChar('N');
			arqCarro.close();
		}catch (IOException e) { 
			System.out.println("Erro na abertura do arquivo  -  programa sera finalizado");
			System.exit(0);
		}
	}

	// ***********************   CONSISTENCIAS   *****************************

	public static boolean consistirMesAnoFab(String mesAnoFab) {
		if (mesAnoFab.length() != 7 || mesAnoFab.charAt(2) != '/') {
			return false;
		}
		try {
			int mes = Integer.parseInt(mesAnoFab.substring(0, 2));
			int ano = Integer.parseInt(mesAnoFab.substring(3));
			return (mes >= 1 && mes <= 12) && (ano >= 1980 && ano <= 2024);
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static boolean consistirCodCarro(String codCarro) {
		if (codCarro.length() != 6) {
			return false;
		}
		String codCarroUpperCase = codCarro.toUpperCase();
		String primeiraParte = codCarroUpperCase.substring(0, 3);
		String segundaParte = codCarroUpperCase.substring(3);
		boolean formato = primeiraParte.matches("[A-Z]{3}") && segundaParte.matches("[0-9]{3}");
		return formato;
	}

	public static String consistirMarca(String marcaDigitada) {
		int indice = 0;
		boolean verificar = false;
		while(!verificar) {
			for(int i=0; i < marcas.length; i++){
				if(marcas[i].equalsIgnoreCase(marcaDigitada)) {	
					verificar = true;
					indice = i;
					break;

				}
			}

			if (!verificar) {
				System.out.println("Marca não encontrada. Digite novamente:");
				marcaDigitada = Main.leia.nextLine();

			}
		}

		return marcas[indice];
	}

	public static String consistirOrigem(String marcaDigitada) {
		int indice = 0;
		boolean verificar = false;
		while(!verificar) {
			for(int i=0; i < marcas.length; i++){
				if(marcas[i].equalsIgnoreCase(marcaDigitada)) {	
					verificar = true;
					indice = i;
					break;

				}
			}

			if (!verificar) {
				System.out.println("Marca não encontrada. Digite novamente:");
				marcaDigitada = Main.leia.nextLine();

			}
		}

		return origens[indice];
	}

	// ***********************   INCLUSAO   *****************************

	public void incluir() {
		String codCarroChave;
		char confirmacao;
		long posicaoRegistro = 0;

		do {			
			do {
				Main.leia.nextLine();
				System.out.println("\n *****  INCLUSÃO DE CARROS  ******* ");
				System.out.print("Digite o código do Carro (FIM para encerrar)....: ");
				codCarroChave = Main.leia.nextLine();
				if (codCarroChave.equalsIgnoreCase("FIM")) {
					return;  // Encerra o método
				}
				if (!consistirCodCarro(codCarroChave)) {
					System.out.println("Erro: Código do carro inválido. Deve ser no formato AAA111, aperte ENTER novamente para tentar novamente");
					continue;  // Volta ao início para solicitar novamente o código
				}
				posicaoRegistro = pesquisarCarro(codCarroChave);

				if (posicaoRegistro >= 0) {
					System.out.println("Carro já cadastrado, digite outro valor\n");
				}
			} while (posicaoRegistro >= 0 || !consistirCodCarro(codCarroChave));

			if (codCarroChave.equalsIgnoreCase("FIM")) {
				break;
			}

			Main.leia.nextLine();

			ativo = 'S';
			codCarro = codCarroChave;
			System.out.print("Digite a marca..................................: ");
			marca = Main.leia.nextLine();
			consistirMarca(marca);
			origemMarca = consistirOrigem(marca);
			System.out.println("Seu país de origem é: " + origemMarca);


			do {
				System.out.print("Digite o modelo ................................: ");
				modelo = Main.leia.nextLine();

				if (modelo.length() < 5) {
					System.out.println("O modelo deve ter pelo menos 5 caracteres. Por favor, tente novamente.");
				}
			} while (modelo.length() < 5);


			do {
				System.out.print("Digite a motorização do carro (lts).............: ");
				String motorizacaoStr = Main.leia.nextLine();
				try {
					motorizacao = Float.parseFloat(motorizacaoStr.replace(",", "."));
					if (motorizacao < 1.0 || motorizacao > 5.0) {
						System.out.print("Digite entre 1.0 e 5.0! ");
					}
				} catch (NumberFormatException e) {
					System.out.print("Entrada inválida. Por favor, digite um número entre 1.0 e 5.0! ");
					motorizacao = -1;
				}
			} while (motorizacao < 1.0 || motorizacao > 5.0);

			do {
				System.out.print("Digite a fabricação do (N ou I).................: ");
				fabricacao = Main.leia.nextLine();
				if (!fabricacao.equalsIgnoreCase("N") && !fabricacao.equalsIgnoreCase("I")) {
					System.out.print("Opção inválida. Por favor, digite 'N' ou 'I'! ");
				}
			} while (!fabricacao.equalsIgnoreCase("N") && !fabricacao.equalsIgnoreCase("I"));

			do {
				System.out.print("Digite a categoria do carro.....................: ");
				Categoria = Main.leia.nextLine();

				if(! Categoria.equalsIgnoreCase("HATCH") && ! Categoria.equalsIgnoreCase("SEDÃ") && ! Categoria.equalsIgnoreCase("SUV") && 
						! Categoria.equalsIgnoreCase("PICAPE") && ! Categoria.equalsIgnoreCase("ESPORTIVO")) {
					System.out.println("Digite uma categoria que seja: HATCH, SEDÃ, SUV, PICAPE, ESPORTIVO");
				}
			}while(! Categoria.equalsIgnoreCase("HATCH") && ! Categoria.equalsIgnoreCase("SEDÃ") && ! Categoria.equalsIgnoreCase("SUV") && 
					! Categoria.equalsIgnoreCase("PICAPE") && ! Categoria.equalsIgnoreCase("ESPORTIVO"));

			do {
				System.out.print("Digite a potência do carro (cv).................: ");
				potencia = Main.leia.nextInt();
				if (potencia <= 0) {
					System.out.print("Digite uma potência maior que 0! ");
				}
			}while(potencia <= 0);

			do {
				System.out.print("Digite o peso do carro (kg).....................: ");
				peso = Main.leia.nextFloat();
				if (peso <= 500) {
					System.out.print("Digite um peso maior 500kg! ");
				}
			}while(peso <= 500);

			do {
				System.out.print("Digite o preço do carro.........................: R$");
				preco = Main.leia.nextFloat();
				if (preco < 10000) {
					System.out.print("Digite um preço maior que R$10.000! ");
				}
			}while(preco < 10000);

			do {
				System.out.print("Digite o mês/ano de fabricação do carro.........: ");
				mesAnoFab = Main.leia.next();
				if (!consistirMesAnoFab(mesAnoFab)) {
					System.out.println("Mês/ano de fabricação inválido. Digite no formato MM/AAAA com um ano maior que 1979.");
				}
			} while (!consistirMesAnoFab(mesAnoFab));

			do {
				System.out.print("\nConfirma a gravacao dos dados (S/N) ? ");
				confirmacao = Main.leia.next().charAt(0);

				if (confirmacao == 'S') {
					salvarCarro();
				} else if (confirmacao != 'N') {
					System.out.println("Erro: Entrada inválida. Por favor, digite 'S' para sim ou 'N' para não.");
				}
			} while (confirmacao != 'S' && confirmacao != 'N');


		}while ( ! codCarro.equalsIgnoreCase("FIM"));	    
	}

	//************************  ALTERACAO  *****************************
	public void alterar() {
		String codCarroChave;
		char confirmacao;
		long posicaoRegistro = 0;
		byte opcao;

		do {
			do {
				Main.leia.nextLine();
				System.out.println("\n ***************  ALTERACAO DE ALUNOS  ***************** ");
				System.out.print("Digite o código do carro que deseja alterar( FIM para encerrar ): ");
				codCarroChave = Main.leia.nextLine();
				if (codCarroChave.equalsIgnoreCase("FIM")) {
					break;
				}

				posicaoRegistro = pesquisarCarro(codCarroChave);
				if (posicaoRegistro == -1) {
					System.out.println("Código nao cadastrada no arquivo, digite outro valor\n");
				}
			}while (posicaoRegistro == -1);

			if (codCarroChave.equalsIgnoreCase("FIM")) {
				break;
			}

			ativo = 'S';

			do {
				System.out.println("[ 1 ] Nome da Marca............: " + marca);
				System.out.println("[ 2 ] Modelo ..................: " + modelo);
				System.out.println("[ 3 ] Motorização..............: " + motorizacao);
				System.out.println("[ 4 ] Fabricação...............: " + fabricacao);
				System.out.println("[ 5 ] Categoria................: " + Categoria);
				System.out.println("[ 6 ] Potência.................: " + potencia);
				System.out.println("[ 7 ] Peso.....................: " + peso);
				System.out.println("[ 8 ] Preço....................: R$" + preco);
				System.out.println("[ 9 ] Mês/Ano..................: " + mesAnoFab);
				System.out.println("Origem.........................: " + consistirOrigem(marca));

				do{
					System.out.println("Digite o numero do campo que deseja alterar (0 para finalizar as alterações): ");
					opcao = Main.leia.nextByte();
				}while (opcao < 0 || opcao > 10);

				switch (opcao) {
				case 1:
					Main.leia.nextLine();
					System.out.print("Digite A NOVA marca.................................: ");
					marca = Main.leia.nextLine();
					consistirMarca(marca);
					origemMarca = consistirOrigem(marca);
					break;
				case 2: 
					Main.leia.nextLine();
					do {
						System.out.print("Digite o NOVO o modelo................................: ");
						modelo = Main.leia.nextLine();

						if (modelo.length() < 5) {
							System.out.println("O modelo deve ter pelo menos 5 caracteres. Por favor, tente novamente.");
						}
					} while (modelo.length() < 5);

					break;
				case 3:
					do {
						System.out.print("Digite a NOVA motorização do carro (lts)............: ");
						String motorizacaoStr = Main.leia.nextLine();
						try {
							motorizacao = Float.parseFloat(motorizacaoStr.replace(",", "."));
							if (motorizacao < 1.0 || motorizacao > 5.0) {
								System.out.print("Digite entre 1.0 e 5.0! ");
							}
						} catch (NumberFormatException e) {
							System.out.print("Entrada inválida. Por favor, digite um número entre 1.0 e 5.0! ");
							motorizacao = -1;
						}
					} while (motorizacao < 1.0 || motorizacao > 5.0);
					break;
				case 4: 
					do {
						System.out.print("Digite a NOVA fabricação do (N ou I)................: ");
						fabricacao = Main.leia.nextLine();
						if (!fabricacao.equalsIgnoreCase("N") && !fabricacao.equalsIgnoreCase("I")) {
							System.out.print("Opção inválida. Por favor, digite 'N' ou 'I'! ");
						}
					} while (!fabricacao.equalsIgnoreCase("N") && !fabricacao.equalsIgnoreCase("I"));
					break;

				case 5: 
					do {
						System.out.print("Digite a NOVA categoria do carro....................: ");
						Categoria = Main.leia.nextLine();

						if(! Categoria.equalsIgnoreCase("HATCH") && ! Categoria.equalsIgnoreCase("SEDÃ") && ! Categoria.equalsIgnoreCase("SUV") && 
								! Categoria.equalsIgnoreCase("PICAPE") && ! Categoria.equalsIgnoreCase("ESPORTIVO")) {
							System.out.println("Digite uma categoria que seja: HATCH, SEDÃ, SUV, PICAPE, ESPORTIVO");
						}
					}while(! Categoria.equalsIgnoreCase("HATCH") && ! Categoria.equalsIgnoreCase("SEDÃ") && ! Categoria.equalsIgnoreCase("SUV") && 
							! Categoria.equalsIgnoreCase("PICAPE") && ! Categoria.equalsIgnoreCase("ESPORTIVO"));
					break;
				case 6: 
					do {
						System.out.print("Digite a NOVA potência do carro (cv)................: ");
						potencia = Main.leia.nextInt();
						if (potencia <= 0) {
							System.out.print("Digite uma potência maior que 0! ");
						}
					}while(potencia <= 0);
					break;
				case 7: 
					do {
						System.out.print("Digite o NOVO peso do carro (kg)....................: ");
						peso = Main.leia.nextFloat();
						if (peso <= 500) {
							System.out.print("Digite um peso maior 500kg! ");
						}
					}while(peso <= 500);
					break;
				case 8: 
					do {
						System.out.print("Digite o NOVO preço do carro........................: R$");
						preco = Main.leia.nextFloat();
						if (preco < 10000) {
							System.out.print("Digite um preço maior que R$10.000! ");
						}
					}while(preco < 10000);
					break;
				case 9: 
					do {
						System.out.print("Digite o NOVO mês/ano de fabricação do carro........: ");
						mesAnoFab = Main.leia.next();
						if (!consistirMesAnoFab(mesAnoFab)) {
							System.out.println("Mês/ano de fabricação inválido. Digite no formato MM/AAAA com um ano maior que 1979.");
						}
					} while (!consistirMesAnoFab(mesAnoFab));
					break;
				}

				System.out.println();
			}while (opcao != 0);  		

			do {
				System.out.print("\nConfirma a gravacao dos dados (S/N) ? ");
				confirmacao = Main.leia.next().charAt(0);

				if (confirmacao == 'S') {
					salvarCarro();
				} else if (confirmacao != 'N') {
					System.out.println("Erro: Entrada inválida. Por favor, digite 'S' para sim ou 'N' para não.");
				}
			} while (confirmacao != 'S' && confirmacao != 'N');

		}while ( ! codCarro.equalsIgnoreCase("FIM"));
	}

	//********  EXCLUSAO  ***********
	public void excluir() {
		String codCarroChave;
		char confirmacao;
		long posicaoRegistro = 0;

		do {
			Main.leia.nextLine();
			System.out.println(" ***************  EXCLUSAO DE CARROS  ***************** ");
			System.out.print("Digite o código do carro que deseja excluir ( FIM para encerrar ): ");
			codCarroChave = Main.leia.nextLine();
			if (codCarroChave.equals("FIM")) {
				break;
			}

			posicaoRegistro = pesquisarCarro(codCarroChave);  // Método que retorna a posição do carro ou -1 se não encontrado
			if (posicaoRegistro == -1) {
				System.out.println("Carro nao cadastrada no arquivo, digite outro valor\n");
			}
		} while (posicaoRegistro == -1);

		if (codCarroChave.equals("FIM")) {
			System.out.println("\n ************  PROGRAMA ENCERRADO  ************** \n");
			return;
		}

		// Exibir detalhes do carro a ser excluído (Assumindo que estas variáveis são globais ou membros da classe)
		System.out.println("[ 1 ] Nome da Marca............: " + marca);
		System.out.println("[ 2 ] Modelo ..................: " + modelo);
		System.out.println("[ 3 ] Motorização..............: " + motorizacao);
		System.out.println("[ 4 ] Fabricação...............: " + fabricacao);
		System.out.println("[ 5 ] Origem ..................: " + origemMarca);
		System.out.println("[ 6 ] Categoria................: " + Categoria);
		System.out.println("[ 7 ] Potência.................: " + potencia);
		System.out.println("[ 8 ] Peso.....................: " + peso);
		System.out.println("[ 9 ] Preço....................: " + preco);
		System.out.println("[ 10 ] Mês/Ano de Fabricação...: " + mesAnoFab);

		do {
			System.out.print("\nConfirma a gravacao dos dados (S/N) ? ");
			confirmacao = Main.leia.next().charAt(0);

			if (confirmacao == 'S') {
				salvarCarro();
			} else if (confirmacao != 'N') {
				System.out.println("Erro: Entrada inválida. Por favor, digite 'S' para sim ou 'N' para não.");
			}
		} while (confirmacao != 'S' && confirmacao != 'N');
	}

	//********  CONSULTAR  ***********

	public void consultar() {
		RandomAccessFile arqCarro;
		byte opcao;
		String filtro;
		long posicaoCursorArquivo = 0;

		do {
			do {
				System.out.println("\n ***************  CONSULTA DE CARROS  ***************** ");
				System.out.println(" [1] LISTAR TODOS OS CARROS DE UMA MARCA INFORMADA ");
				System.out.println(" [2] LISTAR TODOS OS CARROS DE UM ANO DE FABRICAÇÃO INFORMADO ");
				System.out.println(" [3] LISTAR TODOS OS CARROS DE UMA FAIXA DE PREÇO INFORMADA ");
				System.out.println(" [4] LISTAR TODOS OS CARROS ");
				System.out.println(" [0] SAIR");
				System.out.print("\nDigite a opção desejada: ");
				opcao = Main.leia.nextByte();
				if (opcao < 0 || opcao > 4) {
					System.out.println("Opção inválida, digite novamente.\n");
				}
			} while (opcao < 0 || opcao > 4);

			switch (opcao) {
			case 0:
				System.out.println("\n ************  CONSULTA ENCERRADA  ************** \n");
				break;

			case 1:
				Main.leia.nextLine();
				System.out.print("Digite a marca do carro desejado...............: ");
				filtro = Main.leia.nextLine();
				listarCarrosPorMarca(filtro);
				break;

			case 2:
				System.out.print("Digite o ano de fabricação do carro desejado...: ");
				int ano = Main.leia.nextInt();
				listarCarrosPorAno(ano);
				break;

			case 3:
				System.out.print("Digite o preço mínimo..........................: R$");
				float precoMin = Main.leia.nextFloat();
				System.out.print("Digite o preço máximo..........................: R$");
				float precoMax = Main.leia.nextFloat();
				listarCarrosPorFaixaDePreco(precoMin, precoMax);
				break;

			case 4:
				listarTodosOsCarros();
				break;
			}

		} while (opcao != 0);
	}

	private void listarCarrosPorMarca(String marca) {
		try {
			RandomAccessFile arqCarro = new RandomAccessFile("CARROS.DAT", "rw");
			imprimirCabecalho();
			while (true) {
				lerRegistro(arqCarro);
				if (ativo == 'S' && this.marca.equalsIgnoreCase(marca)) {
					imprimirCarro();
				}
			}
		} catch (EOFException e) {
			System.out.println("\n FIM DE RELATÓRIO - ENTER para continuar...\n");
			Main.leia.nextLine();
			Main.leia.nextLine();
		} catch (IOException e) {
			System.out.println("Erro na abertura do arquivo - programa será finalizado");
			System.exit(0);
		}
	}

	private void listarCarrosPorAno(int ano) {
		try {
			RandomAccessFile arqCarro = new RandomAccessFile("CARROS.DAT", "rw");
			imprimirCabecalho();
			while (true) {
				lerRegistro(arqCarro);
				if (ativo == 'S' && mesAnoFab.length() >= 7 && Integer.parseInt(mesAnoFab.substring(3)) == ano) {
					imprimirCarro();
				}
			}
		} catch (EOFException e) {
			System.out.println("\n FIM DE RELATÓRIO - ENTER para continuar...\n");
			Main.leia.nextLine();
			Main.leia.nextLine();
		} catch (IOException e) {
			System.out.println("Erro na abertura do arquivo - programa será finalizado");
			System.exit(0);
		}
	}

	private void listarCarrosPorFaixaDePreco(float precoMin, float precoMax) {
		try {
			RandomAccessFile arqCarro = new RandomAccessFile("CARROS.DAT", "rw");
			imprimirCabecalho();
			while (true) {
				lerRegistro(arqCarro);
				if (ativo == 'S' && preco >= precoMin && preco <= precoMax) {
					imprimirCarro();
				}
			}
		} catch (EOFException e) {
			System.out.println("\n FIM DE RELATÓRIO - ENTER para continuar...\n");
			Main.leia.nextLine();
			Main.leia.nextLine();
		} catch (IOException e) {
			System.out.println("Erro na abertura do arquivo - programa será finalizado");
			System.exit(0);
		}
	}

	private void listarTodosOsCarros() {
		try {
			RandomAccessFile arqCarro = new RandomAccessFile("CARROS.DAT", "rw");
			imprimirCabecalho();
			while (true) {
				lerRegistro(arqCarro);
				if (ativo == 'S') {
					imprimirCarro();
				}
			}
		} catch (EOFException e) {
			System.out.println("\n FIM DE RELATÓRIO - ENTER para continuar...\n");
			Main.leia.nextLine();
			Main.leia.nextLine();
		} catch (IOException e) {
			System.out.println("Erro na abertura do arquivo - programa será finalizado");
			System.exit(0);
		}
	}

	private void lerRegistro(RandomAccessFile arqCarro) throws IOException {
		long posicaoCursorArquivo = arqCarro.getFilePointer();
		ativo = arqCarro.readChar();
		codCarro = arqCarro.readUTF();
		marca = arqCarro.readUTF();
		modelo = arqCarro.readUTF();
		motorizacao = arqCarro.readFloat();
		fabricacao = arqCarro.readUTF();
		origemMarca = arqCarro.readUTF();
		Categoria = arqCarro.readUTF();
		potencia = arqCarro.readInt();
		peso = arqCarro.readFloat();
		preco = arqCarro.readFloat();
		mesAnoFab = arqCarro.readUTF();
	}

	public void imprimirCabecalho() {
		System.out.printf("%-8s %-10s %-10s %-10s %-1s %-10s %-4s %-4s %-6s %-10s %-4s\n",
				"CodCarro", "Marca", "Modelo", "Origem", "F", "Categoria", "Mot", "Pot", "Peso", "Preço", "Ano");
	}

	public void imprimirCarro() {
		System.out.printf("%-8s %-10s %-10s %-10s %-1s %-10s %-4.1f %-4d %-6.1f %-10.2f %-4s\n",
				codCarro, marca, modelo, origemMarca, fabricacao, Categoria, motorizacao, potencia, peso, preco, mesAnoFab);
	}
}