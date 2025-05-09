import java.util.regex.Pattern;
import java.util.Random;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class App {

    public static String validarBandeira(String numeroCartao) {
        // Definição das bandeiras e seus padrões
        String[][] bandeiras = {
            {"Visa", "^4[0-9]{12}(?:[0-9]{3})?$"},
            {"MasterCard", "^(5[1-5][0-9]{14}|2(2[2-9][0-9]{12}|[3-6][0-9]{13}|7[01][0-9]{12}|720[0-9]{12}))$"},
            {"Elo", "^(4011|4312|4389|45|50|627780|636297|636368)[0-9]*$"},
            {"American Express", "^3[47][0-9]{13}$"},
            {"Discover", "^(6011|65|64[4-9])[0-9]*$"},
            {"Hipercard", "^6062[0-9]*$"},
            {"Diners Club", "^3(?:0[0-5]|[68][0-9])[0-9]{11}$"},
            {"EnRoute", "^2(?:014|149)[0-9]{11}$"},
            {"JCB", "^(?:2131|1800|35[0-9]{3})[0-9]{11}$"},
            {"Voyager", "^8699[0-9]{11}$"},
            {"Aura", "^50[0-9]{14,17}$"}
        };

        // Verifica a bandeira do cartão
        for (String[] bandeira : bandeiras) {
            if (Pattern.matches(bandeira[1], numeroCartao)) {
                return bandeira[0];
            }
        }

        return "Bandeira não suportada ou número inválido.";
    }

    public static String gerarDataValidade() {
        // Gera uma data de validade entre 1 e 5 anos no futuro
        LocalDate hoje = LocalDate.now();
        LocalDate validade = hoje.plusYears(new Random().nextInt(5) + 1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
        return validade.format(formatter);
    }

    public static String gerarCVV(String bandeira) {
        // Gera um CVV de 3 ou 4 dígitos dependendo da bandeira
        Random random = new Random();
        if ("American Express".equals(bandeira)) {
            return String.format("%04d", random.nextInt(10000)); // CVV de 4 dígitos
        } else {
            return String.format("%03d", random.nextInt(1000)); // CVV de 3 dígitos
        }
    }

    public static void main(String[] args) {
        // Exemplo de uso
        String numeroCartao = "370623013947503"; // Substitua pelo número do cartão a ser testado
        String bandeira = validarBandeira(numeroCartao);
        System.out.println("Bandeira do cartão: " + bandeira);

        if (!"Bandeira não suportada ou número inválido.".equals(bandeira)) {
            String dataValidade = gerarDataValidade();
            String cvv = gerarCVV(bandeira);
            System.out.println("Data de validade: " + dataValidade);
            System.out.println("CVV: " + cvv);
        }
    }
}