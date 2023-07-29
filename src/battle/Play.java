package battle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlHiddenInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlRadioButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTableDataCell;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.util.NameValuePair;

public class Play {

    public void logAndPlay()
            throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException {
        Thread.sleep(60000 * 10);

        // Cria o cliente
        final WebClient webClient = new WebClient(BrowserVersion.CHROME);
        // O CookieManager vai gerenciar os dados da sess�o
        CookieManager cookieMan = new CookieManager();
        cookieMan = webClient.getCookieManager();
        cookieMan.setCookiesEnabled(true);

        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);

        HtmlPage pagina;
        boolean ok = true;
        do {
            try {
                ok = true;
                webClient.getOptions().setCssEnabled(false);

                // Get the first page
                pagina = webClient.getPage("https://br.battleknight.gameforge.com/");

                List<HtmlForm> formularios = pagina.getForms();
                HtmlForm formulario = null;

                for (HtmlForm htmlForm : formularios) {
                    formulario = htmlForm;
                }

                final HtmlTextInput userId = formulario.getInputByName("loginUsername");
                final HtmlPasswordInput password = formulario.getInputByName("loginPassword");
                final HtmlRadioButtonInput button = (HtmlRadioButtonInput) pagina.getElementById("registerORlogin_1");
                final HtmlSelect select = (HtmlSelect) pagina.getElementById("loginServerSelector");

                // Change the value of the text field
                userId.type("user");
                password.type("pass");
                button.setChecked(true);
                select.setSelectedAttribute("21", true);

                final HtmlPage logando = pagina.getHtmlElementById("loginLink").click();

                this.aguardarJavaScript(webClient);

                logando.getWebResponse();

                while (true) {

                    //int saldo = Integer.parseInt(this.getMoney(webClient));

                    //String c = this.getCustoArteDefensiva(webClient);

                    //int custo = Integer.parseInt(c);

                    //if (saldo >= custo) {
                    //	this.distribuirPontosArteDefensiva(webClient);
                    //}

                    for (int i = 1; i <= 4; i++) {
                        this.trabalhar(webClient);

                        Thread.sleep(60000 * 16);

                        this.pegarSalario(webClient);
                    }

                    this.fazerMissao(webClient);

                    Thread.sleep(60000 * 2);

                }

            } catch (IOException | RuntimeException e) {
                System.out.println(e.getMessage());
                ok = false;
            }

        } while (!ok);
    }

    private void fazerMissao(WebClient webClient)
            throws FailingHttpStatusCodeException, MalformedURLException, IOException {
        final HtmlPage pagina = webClient.getPage("https://s21-br.battleknight.gameforge.com/world/location");
        final HtmlPage escolhendoMissao = pagina.getHtmlElementById("MysticalTower").click();
        this.aguardarJavaScript(webClient);
        HtmlAnchor anchor = pagina.getAnchorByText("Curta: 20 MP");

        final HtmlPage startando = anchor.click();
        this.aguardarJavaScript(webClient);
        System.out.println("Inicio de missao:" + new Date());

    }

    private void trabalhar(WebClient webClient)
            throws FailingHttpStatusCodeException, MalformedURLException, IOException {

        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        this.aguardarJavaScript(webClient);

        HtmlPage pagina = webClient.getPage("https://s21-br.battleknight.gameforge.com/market/work");
        this.aguardarJavaScript(webClient);

        HtmlElement button = (HtmlElement) pagina.createElement("button");
        button.setAttribute("type", "submit");
        List<HtmlForm> formularios = pagina.getForms();
        HtmlForm formulario = null;

        for (HtmlForm htmlForm : formularios) {
            formulario = htmlForm;
        }

        final HtmlHiddenInput horas = formulario.getInputByName("hours");
        final HtmlHiddenInput lado = formulario.getInputByName("side");
        formulario.appendChild(button);

        horas.setValueAttribute("1");
        lado.setValueAttribute("good");

        pagina = button.click();

        this.aguardarJavaScript(webClient);

        System.out.println("Trabalho Iniciado");

    }

    private void pegarSalario(WebClient webClient)
            throws FailingHttpStatusCodeException, MalformedURLException, IOException {
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setThrowExceptionOnScriptError(false);

        final HtmlPage reload = webClient.getPage("https://s21-br.battleknight.gameforge.com/market/work");
        this.aguardarJavaScript(webClient);
        reload.getHtmlElementById("encashLink").click();
        System.out.println("Sal�rio pago");

    }

    private String getMoney(WebClient webClient)
            throws FailingHttpStatusCodeException, MalformedURLException, IOException {
        final HtmlPage reload = webClient.getPage("https://s21-br.battleknight.gameforge.com/world/location");

        this.aguardarJavaScript(webClient);

        DomElement element = reload.getHtmlElementById("silverCount");
        final String saldo = element.asText();
        System.out.println("Saldo: " + saldo + " ------");
        return saldo;

    }

    private String getCustoArteDefensiva(WebClient webClient)
            throws FailingHttpStatusCodeException, MalformedURLException, IOException {
        final HtmlPage page = webClient.getPage("https://s21-br.battleknight.gameforge.com/user/");
        this.aguardarJavaScript(webClient);

        List<HtmlTableDataCell> element = page.getByXPath("//td[@class='attrCost']");
        final String custo = element.get(5).asText();
        System.out.println("Custo para upar AD: " + custo);
        return custo;

    }

    private String getCustoHabilArmas(WebClient webClient)
            throws FailingHttpStatusCodeException, MalformedURLException, IOException {
        final HtmlPage page = webClient.getPage("https://s21-br.battleknight.gameforge.com/user/");
        this.aguardarJavaScript(webClient);

        List<HtmlTableDataCell> element = page.getByXPath("//td[@class='attrCost']");
        final String custo = element.get(4).asText();
        System.out.println("Custo para upar HA: " + custo);
        return custo;

    }

    private String getSorte(WebClient webClient)
            throws FailingHttpStatusCodeException, MalformedURLException, IOException {
        final HtmlPage page = webClient.getPage("https://s21-br.battleknight.gameforge.com/user/");
        this.aguardarJavaScript(webClient);

        List<HtmlTableDataCell> element = page.getByXPath("//td[@class='attrCost']");
        final String custo = element.get(3).asText();
        System.out.println("Custo para upar Sorte: " + custo);
        return custo;

    }

    private void aguardarJavaScript(WebClient webClient) {
        webClient.waitForBackgroundJavaScript(15000);
        webClient.waitForBackgroundJavaScriptStartingBefore(15000);
    }

    private void distribuirPontosArteDefensiva(WebClient webClient)
            throws FailingHttpStatusCodeException, MalformedURLException, IOException {
        final HtmlPage pagina = webClient.getPage("https://s21-br.battleknight.gameforge.com/user/");
        this.aguardarJavaScript(webClient);
        pagina.getHtmlElementById("raiseDefense").click();
        System.out.println("Ponto distribuido em ArteDefensiva");
    }

    private void distribuirPontosHabilArmas(WebClient webClient)
            throws FailingHttpStatusCodeException, MalformedURLException, IOException {
        final HtmlPage pagina = webClient.getPage("https://s21-br.battleknight.gameforge.com/user/");
        this.aguardarJavaScript(webClient);
        pagina.getHtmlElementById("raiseWeapon").click();
        System.out.println("Ponto distribuido em HabilArmas");
    }

    private void distribuirPontosSorte(WebClient webClient)
            throws FailingHttpStatusCodeException, MalformedURLException, IOException {
        final HtmlPage pagina = webClient.getPage("https://s21-br.battleknight.gameforge.com/user/");
        this.aguardarJavaScript(webClient);
        pagina.getHtmlElementById("raiseLuck").click();
        System.out.println("Ponto distribuido em Sorte");
    }

    public static void teste() {
        try {

            URL url = new URL("https://br.battleknight.gameforge.com/");
            URI uri = url.toURI();
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String s;
            while ((s = br.readLine()) != null) {
                System.out.println(s);
                if (s.contains("loginUsername")) {
                    /**
                     *
                     * final long TEMPO = (60000 * 1); Timer timer = new Timer(); TimerTask
                     * timerTask = new TimerTask() {
                     *
                     * @Override public void run() { try { this.testeApi(); } catch
                     *           (FailingHttpStatusCodeException e) { // TODO Auto-generated catch
                     *           block e.printStackTrace(); } catch (MalformedURLException e) { //
                     *           TODO Auto-generated catch block e.printStackTrace(); } catch
                     *           (IOException e) { // TODO Auto-generated catch block
                     *           e.printStackTrace(); } catch (InterruptedException e) { // TODO
                     *           Auto-generated catch block e.printStackTrace(); }
                     *
                     *           } ;
                     *
                     *
                     *
                     *           timer.scheduleAtFixedRate(timerTask, TEMPO, TEMPO); } //
                     *           System.out.println(paginaUser.getWebResponse().getContentAsString());
                     *           //System.out.println(paginaUser.asText()); //
                     *           System.out.println(cookieMan.getCookies());
                     *           //webClient.getOptions().setJavaScriptEnabled(false);
                     *           //paginaUser.getHtmlElementById("raiseDex").click();
                     *
                     *
                     *           https://pt.stackoverflow.com/questions/69188/como-realizar-login-em-um-sistema-por-meio-de-uma-requisi%C3%A7%C3%A3o-http
                     *           https://htmlunit.sourceforge.io/gettingStarted.html
                     *           https://stackoverflow.com/questions/5996559/htmlunit-to-view-source
                     *           https://stackoverflow.com/questions/33196528/login-and-get-webpage-using-api-htmlunit-in-java
                     *           https://stackoverflow.com/questions/21024176/calling-a-javascript-function-with-htmlunit
                     *           https://stackoverflow.com/questions/24442642/htmlunit-how-to-get-button-without-a-name-attribute
                     *           https://stackoverflow.com/questions/15645826/login-with-htmlunit-and-javascript
                     *           https://stackoverflow.com/questions/31496365/java-how-can-i-log-into-a-website-with-htmlunit
                     *           https://stackoverflow.com/questions/17379786/htmlunit-getting-result-of-onclick-function
                     */

                    System.out.println(s);
                }
            }
            br.close();
        } catch (MalformedURLException excecao) {
            excecao.printStackTrace();
        } catch (URISyntaxException excecao) {
            excecao.printStackTrace();
        } catch (IOException excecao) {
            excecao.printStackTrace();
        }

    }

}