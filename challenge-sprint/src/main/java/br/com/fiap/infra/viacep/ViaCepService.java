package br.com.fiap.infra.viacep;
import br.com.fiap.dominio.Endereco;
import br.com.fiap.dominio.User;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;

public class ViaCepService {
    private static final String BASE_URL = "https://viacep.com.br/ws/";

    public boolean isValidCep(String cep, User user) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(BASE_URL + cep + "/json/")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String jsonResponse = response.body().string();
                Gson gson = new Gson();
                CepResponse cepResponse = gson.fromJson(jsonResponse, CepResponse.class);

                if (cepResponse.getCep() != null) {
                    Endereco endereco = new Endereco();
                    endereco.setCep(cepResponse.getCep());
                    endereco.setLogradouro(cepResponse.getLogradouro());
                    endereco.setUf(cepResponse.getUf());
                    user.setEndereco(endereco);
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private class CepResponse {
        private String cep;
        private String logradouro;
        private String uf;

        public String getCep() {
            return cep;
        }

        public String getLogradouro() {
            return logradouro;
        }

        public String getUf() {
            return uf;
        }
    }
}
