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
package io.fabric8.core.spi.internal;

import io.fabric8.core.api.Container;
import io.fabric8.core.api.ContainerIdentity;
import io.fabric8.core.api.ContainerManager;
import io.fabric8.core.api.CreateOptions;
import io.fabric8.core.api.Failure;
import io.fabric8.core.api.JoinOptions;
import io.fabric8.core.api.LockHandle;
import io.fabric8.core.api.ProfileIdentity;
import io.fabric8.core.api.ProvisionEventListener;
import io.fabric8.core.spi.ContainerService;
import io.fabric8.core.spi.permit.PermitManager;
import io.fabric8.core.spi.permit.PermitManager.Permit;
import io.fabric8.core.spi.scr.AbstractComponent;
import io.fabric8.core.spi.scr.ValidatingReference;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jboss.gravia.resource.Version;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

@Component(service = { ContainerManager.class }, configurationPolicy = ConfigurationPolicy.IGNORE, immediate = true)
public final class ContainerManagerImpl extends AbstractComponent implements ContainerManager {

    private final ValidatingReference<PermitManager> permitManager = new ValidatingReference<PermitManager>();

    @Activate
    void activate(Map<String, ?> config) {
        activateComponent();
    }

    @Deactivate
    void deactivate() {
        deactivateComponent();
    }

    @Override
    public LockHandle aquireContainerLock(ContainerIdentity identity) {
        final Permit<ContainerService> permit = permitManager.get().aquirePermit(ContainerService.PERMIT, false);
        final ContainerService service = permit.getInstance();
        final LockHandle writeLock = service.aquireContainerLock(identity);
        return new LockHandle() {
            @Override
            public void unlock() {
                writeLock.unlock();
                permit.release();
            }
        };
    }

    @Override
    public Container createContainer(CreateOptions options) {
        Permit<ContainerService> permit = permitManager.get().aquirePermit(ContainerService.PERMIT, false);
        try {
            ContainerService service = permit.getInstance();
            return service.createContainer(options);
        } finally {
            permit.release();
        }
    }

    @Override
    public Container createContainer(ContainerIdentity identity, CreateOptions options, ProvisionEventListener listener) {
        Permit<ContainerService> permit = permitManager.get().aquirePermit(ContainerService.PERMIT, false);
        try {
            ContainerService service = permit.getInstance();
            return service.createContainer(identity, options, null);
        } finally {
            permit.release();
        }
    }

    @Override
    public Container start(ContainerIdentity identity) {
        Permit<ContainerService> permit = permitManager.get().aquirePermit(ContainerService.PERMIT, false);
        try {
            ContainerService service = permit.getInstance();
            return service.start(identity);
        } finally {
            permit.release();
        }
    }

    @Override
    public Container stop(ContainerIdentity identity) {
        Permit<ContainerService> permit = permitManager.get().aquirePermit(ContainerService.PERMIT, false);
        try {
            ContainerService service = permit.getInstance();
            return service.stop(identity);
        } finally {
            permit.release();
        }
    }

    @Override
    public Container destroy(ContainerIdentity identity) {
        Permit<ContainerService> permit = permitManager.get().aquirePermit(ContainerService.PERMIT, false);
        try {
            ContainerService service = permit.getInstance();
            return service.destroy(identity);
        } finally {
            permit.release();
        }
    }

    @Override
    public Set<ContainerIdentity> getContainerIdentities() {
        Permit<ContainerService> permit = permitManager.get().aquirePermit(ContainerService.PERMIT, false);
        try {
            ContainerService service = permit.getInstance();
            return service.getContainerIdentities();
        } finally {
            permit.release();
        }
    }

    @Override
    public Set<Container> getContainers(Set<ContainerIdentity> identities) {
        Permit<ContainerService> permit = permitManager.get().aquirePermit(ContainerService.PERMIT, false);
        try {
            ContainerService service = permit.getInstance();
            return service.getContainers(identities);
        } finally {
            permit.release();
        }
    }

    @Override
    public Container getCurrentContainer() {
        Permit<ContainerService> permit = permitManager.get().aquirePermit(ContainerService.PERMIT, false);
        try {
            ContainerService service = permit.getInstance();
            return service.getCurrentContainer();
        } finally {
            permit.release();
        }
    }

    @Override
    public Container getContainer(ContainerIdentity identity) {
        Permit<ContainerService> permit = permitManager.get().aquirePermit(ContainerService.PERMIT, false);
        try {
            ContainerService service = permit.getInstance();
            return service.getContainer(identity);
        } finally {
            permit.release();
        }
    }

    @Override
    public Container setVersion(ContainerIdentity identity, Version version, ProvisionEventListener listener) {
        Permit<ContainerService> permit = permitManager.get().aquirePermit(ContainerService.PERMIT, false);
        try {
            ContainerService service = permit.getInstance();
            return service.setVersion(identity, version, listener);
        } finally {
            permit.release();
        }
    }

    @Override
    public boolean ping(ContainerIdentity identity) {
        Permit<ContainerService> permit = permitManager.get().aquirePermit(ContainerService.PERMIT, false);
        try {
            ContainerService service = permit.getInstance();
            return service.ping(identity);
        } finally {
            permit.release();
        }
    }

    @Override
    public Container joinFabric(ContainerIdentity identity, JoinOptions options) {
        Permit<ContainerService> permit = permitManager.get().aquirePermit(ContainerService.PERMIT, false);
        try {
            ContainerService service = permit.getInstance();
            return service.joinFabric(identity, options);
        } finally {
            permit.release();
        }
    }

    @Override
    public Container leaveFabric(ContainerIdentity identity) {
        Permit<ContainerService> permit = permitManager.get().aquirePermit(ContainerService.PERMIT, false);
        try {
            ContainerService service = permit.getInstance();
            return service.leaveFabric(identity);
        } finally {
            permit.release();
        }
    }

    @Override
    public Container addProfiles(ContainerIdentity identity, Set<ProfileIdentity> profiles, ProvisionEventListener listener) {
        Permit<ContainerService> permit = permitManager.get().aquirePermit(ContainerService.PERMIT, false);
        try {
            ContainerService service = permit.getInstance();
            return service.addProfiles(identity, profiles, listener);
        } finally {
            permit.release();
        }
    }

    @Override
    public Container removeProfiles(ContainerIdentity identity, Set<ProfileIdentity> profiles, ProvisionEventListener listener) {
        Permit<ContainerService> permit = permitManager.get().aquirePermit(ContainerService.PERMIT, false);
        try {
            ContainerService service = permit.getInstance();
            return service.removeProfiles(identity, profiles, listener);
        } finally {
            permit.release();
        }
    }

    @Override
    public List<Failure> getFailures(ContainerIdentity identity) {
        Permit<ContainerService> permit = permitManager.get().aquirePermit(ContainerService.PERMIT, false);
        try {
            ContainerService service = permit.getInstance();
            return service.getFailures(identity);
        } finally {
            permit.release();
        }
    }

    @Override
    public List<Failure> clearFailures(ContainerIdentity identity) {
        Permit<ContainerService> permit = permitManager.get().aquirePermit(ContainerService.PERMIT, false);
        try {
            ContainerService service = permit.getInstance();
            return service.clearFailures(identity);
        } finally {
            permit.release();
        }
    }

    @Reference
    void bindPermitManager(PermitManager service) {
        this.permitManager.bind(service);
    }

    void unbindPermitManager(PermitManager service) {
        this.permitManager.unbind(service);
    }
}
