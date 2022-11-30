import java.util.Scanner;

public class BatalhaNavalApp {
    public static void main(String[] args) {

        boolean jogoCarregado = false;
        int escolhaMenu;
        String escolhaJogada;

        Scanner n = new Scanner(System.in);
        do {

            System.out.println("       ***Menu Batalha Naval***\n\n" +
                    "1. Novo jogo:\n" +
                    "2. Jogar:\n" +
                    "3. Sair:");
            escolhaMenu = n.nextInt();
            switch (escolhaMenu) {
                case 1:
                    try {
                        BatalhaNaval.frotaSorteada();
                        BatalhaNaval.alocarFrota();
                        System.out.println("Nova frota carregada!\n");
                        jogoCarregado = true;
                    } catch (Exception e) {
                        throw new RuntimeException("Frota incorreta, dois navios no mesmo lugar ou um navio excedeu o tabuleiro!");
                    }
                    break;
                case 2:
                    if (jogoCarregado) {
                        System.out.println("       ***Menu Batalha Naval***\n\n");
                        BatalhaNaval.apresentarTabuleiro();
                        do {
                            System.out.println("\n00: Voltar ao menu principal\n" +
                                    "11: Revela as frotas\n" +
                                    "10: Atirar!\n" +
                                    "Informe sua jogada:");
                            escolhaJogada = n.next();
                            if (escolhaJogada.equals("10")) {
                                System.out.println("Informe a linha e a coluna que deseja atirar:");
                                try {
                                    BatalhaNaval.atirar(n.next());
                                } catch (Exception e) {
                                    throw new RuntimeException("Erro! informe um lugar correto no tabuleiro!");
                                }
                                BatalhaNaval.apresentarTabuleiro();
                                if (BatalhaNaval.quantidadeDeNavios == 0) {
                                    System.out.println("Parabens, voce ganhou! \nRetornando ao menu!");
                                    escolhaJogada = "00";
                                }
                            } else if (escolhaJogada.equals("11")) {
                                BatalhaNaval.mostrarFrotas();
                            } else if (escolhaJogada.equals("00")) {
                                System.out.println("Retornando ao menu!");
                            } else {
                                System.out.println("Error!\n" +
                                        "Informe um valor de escolha valido!");
                            }
                        } while (!escolhaJogada.equals("00"));
                        break;
                    } else {
                        System.out.println("Erro! Carregue um jogo antes de jogar!\n");
                    }
            }
            if (escolhaMenu == 3) {
                System.exit(0);
            }
            if (escolhaMenu > 3) {
                System.out.println("Error!\n" +
                        "Informe um valor de escolha valido!");
            }
        } while (escolhaMenu != 3);
    }
}