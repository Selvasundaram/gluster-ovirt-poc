package org.ovirt.engine.core.bll.gluster;

import java.util.List;

import org.ovirt.engine.core.bll.Backend;
import org.ovirt.engine.core.common.AuditLogType;
import org.ovirt.engine.core.common.businessentities.GlusterBrickEntity;
import org.ovirt.engine.core.common.businessentities.GlusterVolumeEntity;
import org.ovirt.engine.core.common.errors.VdcBLLException;
import org.ovirt.engine.core.common.glusteractions.GlusterVolumeBricksParameters;
import org.ovirt.engine.core.common.glustercommands.GlusterVolumeBricksVDSParameters;
import org.ovirt.engine.core.common.vdscommands.VDSCommandType;
import org.ovirt.engine.core.common.vdscommands.VDSReturnValue;
import org.ovirt.engine.core.compat.Guid;
import org.ovirt.engine.core.dal.VdcBllMessages;
import org.ovirt.engine.core.dal.dbbroker.DbFacade;
import org.ovirt.engine.core.utils.transaction.TransactionMethod;
import org.ovirt.engine.core.utils.transaction.TransactionSupport;

public class RemoveBricksFromGlusterVolumeCommand extends GlusterCommandBase<GlusterVolumeBricksParameters> {

    public RemoveBricksFromGlusterVolumeCommand(GlusterVolumeBricksParameters params) {
        super(params);
    }

    @Override
    protected boolean canDoAction() {
        addCanDoActionMessage(VdcBllMessages.VAR__ACTION__REMOVE);
        addCanDoActionMessage(VdcBllMessages.VAR__TYPE__GLUSTER_BRICK);
        return super.canDoAction();
    }

    @Override
    protected void executeCommand() {
        try {
            TransactionSupport.executeInNewTransaction(new TransactionMethod<Void>() {

                @Override
                public Void runInTransaction() {
                    deleteGlusterVolumeBricksInDb(getVdsGroupId(),
                            getParameters().getVolumeName(),
                            getParameters().getBricks());
                    VDSReturnValue returnValue =
                            Backend.getInstance()
                                    .getResourceManager()
                                    .RunVdsCommand(VDSCommandType.RemoveBricksFromGlusterVolume,
                                            new GlusterVolumeBricksVDSParameters(getOnlineHost().getvds_id(),
                                                    getParameters().getVolumeName(),
                                                    getParameters().getBricks()));
                    setSucceeded(returnValue.getSucceeded());
                    return null;
                }
            });
        } catch (VdcBLLException e) {
            getReturnValue().getExecuteFailedMessages().add(e.getErrorCode().toString());
            setSucceeded(false);
        }
    }

    private void deleteGlusterVolumeBricksInDb(Guid vdsGroupId, String volumeName, List<GlusterBrickEntity> bricks) {
        GlusterVolumeEntity volume = DbFacade.getInstance().getGlusterVolumeDAO().getByName(vdsGroupId, volumeName);
        DbFacade.getInstance()
                .getGlusterVolumeDAO()
                .removeBricksFromVolume(volume.getClusterId(), volume.getId(), bricks);
    }

    @Override
    public AuditLogType getAuditLogTypeValue() {
        if(getSucceeded()) {
            return AuditLogType.GLUSTER_VOLUME_REMOVE_BRICK;
        } else {
            return AuditLogType.GLUSTER_VOLUME_REMOVE_BRICK_FAILED;
        }
    }
}
