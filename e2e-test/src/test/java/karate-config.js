function fn() {
    var env = karate.env; // get system property 'karate.env'
    karate.log('karate.env system property was:', env);
    if (!env) {
        env = 'dev';
    }
    var config = {
        env: env,
        baseUrl: ''
    }
    if (env == 'dev') {
        config.baseUrl = 'http://localhost:8080';
    } else if (env == 'e2e') {
        config.baseUrl = karate.properties['gateway.base.url'];
    }

    karate.log('gateway.base.ur system property was:', config.baseUrl);

    return config;
}