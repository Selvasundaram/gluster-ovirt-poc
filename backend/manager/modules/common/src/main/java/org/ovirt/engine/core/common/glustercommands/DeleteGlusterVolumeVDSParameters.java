package org.ovirt.engine.core.common.glustercommands;

import org.ovirt.engine.core.compat.Guid;

public class DeleteGlusterVolumeVDSParameters extends GlusterBaseVDSCommandParameters {

    private String volumeName;

    public DeleteGlusterVolumeVDSParameters(Guid storagePoolId, String volumeName) {
        super(storagePoolId);
        setVolumeName(volumeName);
    }

    public String getVolumeName() {
        return volumeName;
    }

    public void setVolumeName(String volumeName) {
        this.volumeName = volumeName;
    }
}
