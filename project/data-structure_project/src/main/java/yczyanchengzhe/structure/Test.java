package yczyanchengzhe.structure;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.util.Map;

/**
 * @ClassName: Test
 * @Description: TODO
 * @Create by: A
 * @Date: 2021/1/1 11:57
 */
public class Test {

    @SneakyThrows
    public static void main(String[] args) {

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> map = objectMapper.readValue("{\n" +
                "  \"jekyll\": \"3.9.0\",\n" +
                "  \"jekyll-sass-converter\": \"1.5.2\",\n" +
                "  \"kramdown\": \"2.3.0\",\n" +
                "  \"kramdown-parser-gfm\": \"1.1.0\",\n" +
                "  \"jekyll-commonmark-ghpages\": \"0.1.6\",\n" +
                "  \"liquid\": \"4.0.3\",\n" +
                "  \"rouge\": \"3.23.0\",\n" +
                "  \"github-pages-health-check\": \"1.16.1\",\n" +
                "  \"jekyll-redirect-from\": \"0.16.0\",\n" +
                "  \"jekyll-sitemap\": \"1.4.0\",\n" +
                "  \"jekyll-feed\": \"0.15.1\",\n" +
                "  \"jekyll-gist\": \"1.5.0\",\n" +
                "  \"jekyll-paginate\": \"1.1.0\",\n" +
                "  \"jekyll-coffeescript\": \"1.1.1\",\n" +
                "  \"jekyll-seo-tag\": \"2.6.1\",\n" +
                "  \"jekyll-github-metadata\": \"2.13.0\",\n" +
                "  \"jekyll-avatar\": \"0.7.0\",\n" +
                "  \"jekyll-remote-theme\": \"0.4.2\",\n" +
                "  \"jemoji\": \"0.12.0\",\n" +
                "  \"jekyll-mentions\": \"1.6.0\",\n" +
                "  \"jekyll-relative-links\": \"0.6.1\",\n" +
                "  \"jekyll-optional-front-matter\": \"0.3.2\",\n" +
                "  \"jekyll-readme-index\": \"0.3.0\",\n" +
                "  \"jekyll-default-layout\": \"0.1.4\",\n" +
                "  \"jekyll-titles-from-headings\": \"0.5.3\",\n" +
                "  \"jekyll-swiss\": \"1.0.0\",\n" +
                "  \"minima\": \"2.5.1\",\n" +
                "  \"jekyll-theme-primer\": \"0.5.4\",\n" +
                "  \"jekyll-theme-architect\": \"0.1.1\",\n" +
                "  \"jekyll-theme-cayman\": \"0.1.1\",\n" +
                "  \"jekyll-theme-dinky\": \"0.1.1\",\n" +
                "  \"jekyll-theme-hacker\": \"0.1.2\",\n" +
                "  \"jekyll-theme-leap-day\": \"0.1.1\",\n" +
                "  \"jekyll-theme-merlot\": \"0.1.1\",\n" +
                "  \"jekyll-theme-midnight\": \"0.1.1\",\n" +
                "  \"jekyll-theme-minimal\": \"0.1.1\",\n" +
                "  \"jekyll-theme-modernist\": \"0.1.1\",\n" +
                "  \"jekyll-theme-slate\": \"0.1.1\",\n" +
                "  \"jekyll-theme-tactile\": \"0.1.1\",\n" +
                "  \"jekyll-theme-time-machine\": \"0.1.1\",\n" +
                "  \"ruby\": \"2.7.1\",\n" +
                "  \"github-pages\": \"209\",\n" +
                "  \"html-pipeline\": \"2.14.0\",\n" +
                "  \"sass\": \"3.7.4\",\n" +
                "  \"safe_yaml\": \"1.0.5\",\n" +
                "  \"nokogiri\": \"1.10.10\"\n" +
                "}", Map.class);

        for (Map.Entry<String, String> entry : map.entrySet()) {

            System.out.printf("gem '%s', '~> %s'\n", entry.getKey(), entry.getValue());
        }
    }
}
