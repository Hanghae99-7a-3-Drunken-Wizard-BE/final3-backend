package com.example.game.security;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

public class FilterSkipMatcher implements RequestMatcher {

    private final OrRequestMatcher orRequestMatcher;
    private final RequestMatcher   processingMatcher;

    public FilterSkipMatcher(
            List<String> pathToSkip,
            String processingPath
    ) {
        this.orRequestMatcher = new OrRequestMatcher(pathToSkip
                .stream()
                .map(this :: httpPath)
                .collect(Collectors.toList()));
        this.processingMatcher = new AntPathRequestMatcher(processingPath);
    }

    private AntPathRequestMatcher httpPath(String skipPath) {
        String[] splitStr = skipPath.split(",");

        return new AntPathRequestMatcher(
                splitStr[1],
                splitStr[0]
        );
    }

    @Override
    public boolean matches(HttpServletRequest req) {
        return !orRequestMatcher.matches(req) && processingMatcher.matches(req);
    }
}
