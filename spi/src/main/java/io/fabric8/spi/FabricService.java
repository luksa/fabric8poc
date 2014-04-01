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
package io.fabric8.spi;

import io.fabric8.api.Identity;
import io.fabric8.spi.permit.PermitState;

/**
 * The internal fabric service
 *
 * @author Thomas.Diesler@jboss.com
 * @since 14-Mar-2014
 */
public interface FabricService {

    /**
     * A key into the configuration that names the container name prefix created by the current service instance
     */
    String KEY_NAME_PREFIX = "container.prefix";

    /**
     * The configuration PID for this service
     */
    String FABRIC_SERVICE_PID = "fabric.service.pid";

    /**
     * The {@link PermitState} that protects this service.
     */
    PermitState<FabricService> PERMIT = new PermitState<FabricService>(FabricService.class);

    /**
     * Create a container with a given name
     * @throws IllegalStateException if a container with the given name already exists
     */
    ContainerState createContainer(String name);

    /**
     * Get a container with a given identity
     * @return the container or <code>null</code>
     */
    ContainerState getContainerByName(Identity identity);

    /**
     * Start the container with the given identity
     * @throws IllegalStateException if the container does not exist
     */
    ContainerState startContainer(Identity identity);

    /**
     * Stop the container with the given identity
     * @throws IllegalStateException if the container does not exist
     */
    ContainerState stopContainer(Identity identity);

    /**
     * Destroy the container with the given identity
     * @throws IllegalStateException if the container does not exist
     */
    ContainerState destroyContainer(Identity identity);
}