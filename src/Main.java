import java.util.Random;
import java.util.Scanner;

public class Main {
    private int linha;  //armazenam o número de linhas
    private int coluna;  //armazenam o número de colunas
    private int[][] grade;//Matriz que representa a grade celular
    private Random random; // Declara a variável Random
    private Scanner scanner; // Declara a variável Scanner

    public Main(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
        grade = new int[linha][coluna];
        random = new Random(); // Inicializa o gerador de números aleatórios
        scanner = new Scanner(System.in); // Inicializa o scanner para entrada do usuário
    }

    // Método de inicialização modificado para gerar um estado aleatório
    public void gradeAleatorio() {
        for (int i = 0; i < linha; i++) { //Preenche a grade com valores de 0 ou 1 para começar o estado inicial
            for (int j = 0; j < coluna; j++) {
                grade[i][j] = random.nextInt(2); // Preenche aleatoriamente com 0 ou 1
            }
        }
    }

    public void update() {
        int[][] novagrade = new int[linha][coluna]; //cria uma nova grade para armazenar o novo estado do jogo

        for (int i = 0; i < linha; i++) {
            for (int j = 0; j < coluna; j++) {
                int VizinhosVivos = ContagemDevizinhos(i, j);// para cada celular se conta o numero de vizinhos vivos

                if (grade[i][j] == 1) {
                    // Regra 2: Qualquer célula viva com menos de 2 vizinhos vivos morre (subpopulação).
                    //Regra 3: Qualquer célula viva com mais de 3 vizinhos vivos morre (superpopulação).
                    if (VizinhosVivos < 2 || VizinhosVivos > 3) {
                        novagrade[i][j] = 0;
                    } else {
                        novagrade[i][j] = 1; //  Qualquer célula viva com 2 ou 3 vizinhos vivos continua viva.
                    }
                } else {
                    //  Qualquer célula morta com exatamente 3 vizinhos vivos torna-se uma célula viva
                    if (VizinhosVivos == 3) {
                        novagrade[i][j] = 1;
                    }
                }
            }
        }

        grade = novagrade;
    }

    private int ContagemDevizinhos(int linha, int coluna) {// metodo com declaração privada, so pode ser chamado pela class main
        int contador = 0;  //Variável que mantém a contagem do número de células vizinhas vivas.
        int[] vizinhos = {-1, 0, 1}; //deslocamentos possiveis

        for (int i : vizinhos) {
            for (int j : vizinhos) {
                if (i == 0 && j == 0) continue;

                int novaLinha = linha + i;//indica a localização atual da celula
                int novaColuna = coluna + j;//indica a localização atual da celula

                if (novaLinha >= 0 && novaLinha < this.linha && novaColuna >= 0 && novaColuna < this.coluna) {
                    contador += grade[novaLinha][novaColuna];
                }
            }
        }

        return contador;
    }

    public void display() {
        for (int i = 0; i < linha; i++) {
            for (int j = 0; j < coluna; j++) {
                System.out.print(grade[i][j] == 1 ? " . " : " 0 "); // Adiciona espaço após cada caractere
            }
            System.out.println(); // Nova linha após cada linha da grade
        }
    }

    public void inicializador() {
        gradeAleatorio(); // Inicializa com células aleatórias

        int geração = 0;
        while (true) {
            System.out.println("Geração " + (geração + 1) + ":");
            display();
            update();
            geração++;

            // Verifica se o usuário deseja sair
            System.out.print("Pressione Enter para continuar ou ‘S’ para sair:  ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("s")) {
                break;
            }
        }

        scanner.close(); // Fecha o scanner
        System.out.println("Fim da geração");
    }

    public static void main(String[] args) {
        Main game = new Main(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        game.inicializador(); // Inicia o jogo com loop infinito
    }
}

