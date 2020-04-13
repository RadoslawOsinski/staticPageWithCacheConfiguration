FROM caddy/caddy:2.0.0-rc.2
COPY staticAppWithCacheCaddy/src/docker/Caddyfile /etc/caddy/Caddyfile
COPY staticAppWithCacheCaddy/src/docker/staticappwithcache.devcwsfe.pl/cert.pem /etc/caddy/certs/staticappwithcache.devcwsfe.pl/
COPY staticAppWithCacheCaddy/src/docker/staticappwithcache.devcwsfe.pl/key.pem /etc/caddy/certs/staticappwithcache.devcwsfe.pl/
