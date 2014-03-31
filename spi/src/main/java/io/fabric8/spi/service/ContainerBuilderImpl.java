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
package io.fabric8.spi.service;

import io.fabric8.api.Container;
import io.fabric8.api.ContainerBuilder;
import io.fabric8.api.Node;
import io.fabric8.spi.FabricService;
import io.fabric8.spi.permit.PermitManager;
import io.fabric8.spi.permit.PermitManager.Permit;

import org.jboss.gravia.runtime.RuntimeType;

final class ContainerBuilderImpl implements ContainerBuilder {

    private final PermitManager permitManager;
    private String identity;

    ContainerBuilderImpl(PermitManager permitManager) {
        this.permitManager = permitManager;
    }

    @Override
    public ContainerBuilder setRuntimeType(RuntimeType type) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ContainerBuilder addIdentity(String identity) {
        this.identity = identity;
        return this;
    }

    @Override
    public ContainerBuilder setNode(Node node) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Container createContainer() {
        Permit<FabricService> permit = permitManager.aquirePermit(FabricService.PERMIT, false);
        try {
            FabricService fabricService = permit.getInstance();
            return new ContainerImpl(permitManager, fabricService.createContainer(identity));
        } finally {
            permit.release();
        }
    }
}
