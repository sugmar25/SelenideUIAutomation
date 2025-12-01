package org.gallup.access.utils;

import com.bettercloud.vault.Vault;
import com.bettercloud.vault.VaultConfig;
import com.bettercloud.vault.response.LogicalResponse;

import java.util.Map;

public class VaultSecrets {

    private final Vault vault;

    public VaultSecrets(String vaultAddr, String token) throws Exception {
        VaultConfig config = new VaultConfig()
                .address(vaultAddr)              // "http://127.0.0.1:8200"
                .token(token)
                .build();
        this.vault = new Vault(config);
    }

    // For KV v2, you typically read from "secret/data/<name>"
    public String getDbPasswordKv2(String secretName, String key) throws Exception {
        String path = "secret/data/" + secretName;        // e.g., "db-prod"
        LogicalResponse resp = vault.logical().read(path);
        Map<String, Object> data = (Map<String, Object>) resp.getData().keySet();
        return (String) data.get(key);                    // "password"
    }
}
