import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class APIClient implements ScoreClient {
    @Override
    public int getScore(String cpf) {
        try {
            String cleanCpf = cpf.replaceAll("[^\\d]", "");  // Deixa apenas os dígitos
            System.out.println("Formatted CPF: " + cleanCpf);  // Log para verificar o CPF formatado

            if (cleanCpf.length() != 11) {
                throw new RuntimeException("Invalid CPF format: " + cleanCpf);
            }

            String url = "https://score.hsborges.dev/api/score?cpf=" + cleanCpf;
            System.out.println("Requesting URL: " + url);  // Log da URL sendo requisitada
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            System.out.println("Response Code: " + responseCode); // Log resposta

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Conversão de JSON
                JSONObject jsonResponse = new JSONObject(response.toString());
                return jsonResponse.getInt("score");
            } else if (responseCode == 429) {
                System.out.println("Muitas solicitações. Tente novamente mais tarde.");
                return -1;
            } else {
                throw new RuntimeException("Falha ao obter pontuação para CPF: " + cleanCpf + ". Response Code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}