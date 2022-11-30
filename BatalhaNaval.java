import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.Scanner;
import java.lang.Enum;

public class BatalhaNaval {

    static String[][] tabuleiro = new String[15][15];
    static String[] vetorDeAcertos;
    static int quantidadeDeNavios = 0, tamanhoVetorAcertos = 0, ctgAcertos = 0;

    public static void apresentarTabuleiro() {

        int ctg = 1;

        System.out.println("    A  |   B   |   C   |   D   |   E   |   F   |   G   |   H   |   I   |   J   |   K   |   L   |   M   |   N   |   O   |");
        for (int i = 0; i < tabuleiro.length; i++) {
            if (i >= 1) {
                System.out.println();
            }
            System.out.printf(ctg + " ");
            ctg++;
            for (int j = 0; j < tabuleiro.length; j++) {
                if (tabuleiro[i][j] == null || tabuleiro[i][j] == "navio") {
                    if (i < 9) {
                        System.out.printf("     |  ");
                    } else {
                        System.out.printf("    |   ");
                    }
                } else if (tabuleiro[i][j] != null && tabuleiro[i][j] == "x") {
                    if (i < 9) {
                        System.out.printf(" " + tabuleiro[i][j] + "   |  ");
                    } else {
                        System.out.printf("" + tabuleiro[i][j] + "   |   ");
                    }
                } else if (tabuleiro[i][j] != null && tabuleiro[i][j] == "0") {
                    if (i < 9) {
                        System.out.printf(" " + tabuleiro[i][j] + "   |  ");
                    } else {
                        System.out.printf("" + tabuleiro[i][j] + "   |   ");
                    }
                } else if (tabuleiro[i][j] != null && tabuleiro[i][j] == "-") {
                    if (i < 9) {
                        System.out.printf(" " + tabuleiro[i][j] + "   |  ");
                    } else {
                        System.out.printf("" + tabuleiro[i][j] + "   |   ");
                    }
                }
            }
        }
        System.out.println("\nNavios restantes: " + quantidadeDeNavios);
        System.out.println();
    }

    public static void atirar(String tiro) throws Exception {
        ConversaoParaInteiros n = Enum.valueOf(ConversaoParaInteiros.class, String.valueOf(tiro.charAt(0)).toUpperCase());
        int valorInformado = 0;
        int a = n.getValorLetra();

        if (a > 16 || conversorParaInteiros(tiro) > 16) {
            throw new Exception("");
        }


        for (int i = 0; i < vetorDeAcertos.length; i++) {
            if (vetorDeAcertos[i] != null && vetorDeAcertos[i].equals(tiro)) {
                valorInformado++;
                break;
            }
        }
        if (valorInformado > 0) {
            System.out.println("Erro! Valor ja informado!");
        } else if (tabuleiro[conversorParaInteiros(tiro) - 1][a - 1] != null) {
            vetorDeAcertos[ctgAcertos] = tiro;
            ctgAcertos++;
            tabuleiro[conversorParaInteiros(tiro) - 1][a - 1] = "x";
            System.out.println("Acertou!");
            try {
                acertoDeNavios();
            } catch (IOException e) {
                throw new RuntimeException("Erro!");
            }
        } else {
            tabuleiro[conversorParaInteiros(tiro) - 1][a - 1] = "0";
            System.out.println("Errou!");
        }
    }

    public static void frotaSorteada() throws IOException {
        Path path = Paths.get(sortearFrota());
        Path pathSorteado = Paths.get("src/ressources/FrotaSorteada.txt");
        Scanner frota = new Scanner(path.toFile());
        FileWriter escritaFrota = new FileWriter(pathSorteado.toFile());
        while (frota.hasNextLine()) {
            escritaFrota.write(frota.nextLine() + "\n");
        }
        escritaFrota.close();
    }

    public static void mostrarFrotas() {
        int ctg = 1;
        System.out.println("    A  |   B   |   C   |   D   |   E   |   F   |   G   |   H   |   I   |   J   |   K   |   L   |   M   |   N   |   O   |");
        for (int i = 0; i < tabuleiro.length; i++) {
            if (i >= 1) {
                System.out.println();
            }
            System.out.printf(ctg + " ");
            ctg++;
            for (int j = 0; j < tabuleiro.length; j++) {
                if (tabuleiro[i][j] == null) {
                    if (i < 9) {
                        System.out.printf("     |  ");
                    } else {
                        System.out.printf("    |   ");
                    }
                } else if (tabuleiro[i][j] != null && tabuleiro[i][j] == "navio") {
                    if (i < 9) {
                        System.out.printf(" x   |  ");
                    } else {
                        System.out.printf("x   |   ");
                    }
                }
            }
        }
        System.out.println();
    }

    public static void alocarFrota() throws Exception {
        Path frotaEscolhida = Paths.get("src/ressources/FrotaSorteada.txt");
        Scanner frota = new Scanner(frotaEscolhida.toFile());
        limparTabuleiro();

        while (frota.hasNextLine()) {

            quantidadeDeNavios++;

            String data = frota.nextLine();
            for (String s : data.split(",")) {
                ConversaoParaInteiros n = Enum.valueOf(ConversaoParaInteiros.class, String.valueOf(s.charAt(0)).toUpperCase());
                int a = n.getValorLetra();
                tamanhoVetorAcertos++;

                if (tabuleiro[conversorParaInteiros(s) - 1][a - 1] == null) {
                    tabuleiro[conversorParaInteiros(s) - 1][a - 1] = "navio";
                } else {
                    throw new Exception("");
                }
            }
        }
        vetorDeAcertos = new String[tamanhoVetorAcertos];
    }

    public static void limparTabuleiro() {
        quantidadeDeNavios=0;
        for (int i = 0; i < tabuleiro.length; i++) {
            for (int j = 0; j < tabuleiro.length; j++) {
                tabuleiro[i][j] = null;
            }
        }
    }

    public static void acertoDeNavios() throws IOException {

        Path frotaEscolhida = Paths.get("src/ressources/FrotaSorteada.txt");
        Scanner frota = new Scanner(frotaEscolhida.toFile());

        while (frota.hasNextLine()) {
            int tamanhoDoNavio = 0, ctgAcerto = 0;
            String data = frota.nextLine();
            for (String elemento : data.split(",")) {
                tamanhoDoNavio++;
            }
            for (String s : data.split(",")) {
                for (int i = 0; i < vetorDeAcertos.length; i++) {
                    if (vetorDeAcertos[i] != null && s.equals(vetorDeAcertos[i])) {
                        ctgAcerto++;
                    }
                }
            }
            if (ctgAcerto == tamanhoDoNavio) {
                for (String s : data.split(",")) {
                    ConversaoParaInteiros n = Enum.valueOf(ConversaoParaInteiros.class, String.valueOf(s.charAt(0)).toUpperCase());
                    int a = n.getValorLetra();
                    if (tabuleiro[conversorParaInteiros(s) - 1][a - 1].equals("-")) {
                        break;
                    } else {
                        for (String f : data.split(",")) {
                            ConversaoParaInteiros z = Enum.valueOf(ConversaoParaInteiros.class, String.valueOf(f.charAt(0)).toUpperCase());
                            int h = z.getValorLetra();
                            tabuleiro[conversorParaInteiros(f) - 1][h - 1] = "-";
                        }
                        quantidadeDeNavios--;
                        break;
                    }
                }
            }
        }
    }

    public static int conversorParaInteiros(String s) {
        String b, c = "";
        int d;

        b = (String.valueOf(s.charAt(1)));
        if (s.length() > 2) {
            c = (String.valueOf(s.charAt(2)));
            d = Integer.parseInt(b + c);
        } else {
            d = Integer.parseInt(b);
        }
        return d;
    }

    public static String sortearFrota() {
        Random random = new Random();
        int numero = random.nextInt(1, 11);
        return ("src/ressources/" + numero + ".txt");
    }
}