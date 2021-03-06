/*
 * #%L
 * Gravia :: Integration Tests :: Common
 * %%
 * Copyright (C) 2010 - 2014 JBoss by Red Hat
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package io.fabric8.core.service;

import io.fabric8.core.api.ConfigurationProfileItem;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.jboss.gravia.Constants;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class ProfileSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileSupport.class);

    // Hide ctor
    private ProfileSupport() {
    }

    static void applyConfigurationItems(ConfigurationAdmin configAdmin, Set<ConfigurationProfileItem> items) {
        for (ConfigurationProfileItem item : items) {
            LOGGER.info("Apply configuration item: {}", item);
            try {
                Configuration config = configAdmin.getConfiguration(item.getIdentity(), null);
                Map<String, Object> prevConfig = toMap(config.getProperties());
                Map<String, Object> nextConfig = item.getConfiguration();
                if (needsUpdate(prevConfig, nextConfig)) {
                    config.update(toDictionary(nextConfig));
                }
            } catch (IOException ex) {
                throw new IllegalStateException("Cannot update configuration: " + item.getIdentity(), ex);
            }
        }
    }

    private static boolean needsUpdate(Map<String, Object> prevConfig, Map<String, Object> nextConfig) {
        prevConfig.remove(Constants.SERVICE_PID);
        nextConfig = new HashMap<String, Object>(nextConfig);
        nextConfig.remove(Constants.SERVICE_PID);
        return !nextConfig.equals(prevConfig);
    }

    private static Dictionary<String, Object> toDictionary(Map<String, Object> config) {
        Dictionary<String, Object> result = new Hashtable<String, Object>();
        if (config != null) {
            for (Entry<String, Object> entry : config.entrySet()) {
                result.put(entry.getKey(), entry.getValue());
            }
        }
        return result;
    }

    private static Map<String, Object> toMap(Dictionary<String, Object> config) {
        Map<String, Object> result = new HashMap<String, Object>();
        if (config != null) {
            Enumeration<String> keys = config.keys();
            while (keys.hasMoreElements()) {
                String key = keys.nextElement();
                result.put(key, config.get(key));
            }
        }
        return result;
    }
}
