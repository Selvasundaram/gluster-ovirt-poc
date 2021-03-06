package org.ovirt.engine.ui.uicommonweb.models.vms;
import java.util.Collections;
import org.ovirt.engine.core.compat.*;
import org.ovirt.engine.ui.uicompat.*;
import org.ovirt.engine.core.common.businessentities.*;
import org.ovirt.engine.core.common.vdscommands.*;
import org.ovirt.engine.core.common.queries.*;
import org.ovirt.engine.core.common.action.*;
import org.ovirt.engine.ui.frontend.*;
import org.ovirt.engine.ui.uicommonweb.*;
import org.ovirt.engine.ui.uicommonweb.models.*;
import org.ovirt.engine.core.common.*;


import org.ovirt.engine.core.common.interfaces.*;
import org.ovirt.engine.core.common.businessentities.*;
import org.ovirt.engine.ui.uicompat.*;
import org.ovirt.engine.ui.uicommonweb.*;
import org.ovirt.engine.ui.uicommonweb.models.*;

@SuppressWarnings("unused")
public class MigrateModel extends Model
{

	private ListModel privateHosts;
	public ListModel getHosts()
	{
		return privateHosts;
	}
	private void setHosts(ListModel value)
	{
		privateHosts = value;
	}

	private java.util.ArrayList<VM> privateVmList;
	public java.util.ArrayList<VM> getVmList()
	{
		return privateVmList;
	}
	public void setVmList(java.util.ArrayList<VM> value)
	{
		privateVmList = value;
	}

	private boolean privateVmsOnSameCluster;
	public boolean getVmsOnSameCluster()
	{
		return privateVmsOnSameCluster;
	}
	public void setVmsOnSameCluster(boolean value)
	{
		privateVmsOnSameCluster = value;
	}

	private boolean isAutoSelect;
	public boolean getIsAutoSelect()
	{
		return isAutoSelect;
	}
	public void setIsAutoSelect(boolean value)
	{
		if (isAutoSelect != value)
		{
			isAutoSelect = value;
			getHosts().setIsChangable(!isAutoSelect);
			OnPropertyChanged(new PropertyChangedEventArgs("IsAutoSelect"));
			setIsSameVdsMessageVisible(!value);
			privateSelectHostAutomatically_IsSelected.setEntity(value);
			privateSelectDestinationHost_IsSelected.setEntity(!value);
			privateHosts.setIsChangable(!value);
		}
	}

	private boolean isHostSelAvailable;
	public boolean getIsHostSelAvailable()
	{
		return isHostSelAvailable;
	}
	public void setIsHostSelAvailable(boolean value)
	{
		if (isHostSelAvailable != value)
		{
			isHostSelAvailable = value;
			OnPropertyChanged(new PropertyChangedEventArgs("IsHostSelAvailable"));
		}
	}

	private boolean noSelAvailable;
	public boolean getNoSelAvailable()
	{
		return noSelAvailable;
	}
	public void setNoSelAvailable(boolean value)
	{
		if (noSelAvailable != value)
		{
			noSelAvailable = value;
			OnPropertyChanged(new PropertyChangedEventArgs("NoSelAvailable"));
		}
	}


	private boolean isSameVdsMessageVisible;
	public boolean getIsSameVdsMessageVisible()
	{
		return isSameVdsMessageVisible;
	}
	public void setIsSameVdsMessageVisible(boolean value)
	{
		isSameVdsMessageVisible = value & gethasSameVdsMessage() & !getIsAutoSelect();
		OnPropertyChanged(new PropertyChangedEventArgs("IsSameVdsMessageVisible"));
	}


	//OnPropertyChanged(new PropertyChangedEventArgs("IsSameVdsMessageVisible"));
	private boolean privatehasSameVdsMessage;
	public boolean gethasSameVdsMessage()
	{
		return privatehasSameVdsMessage;
	}
	public void sethasSameVdsMessage(boolean value)
	{
		privatehasSameVdsMessage = value;
	}

	private EntityModel privateSelectHostAutomatically_IsSelected;
	public EntityModel getSelectHostAutomatically_IsSelected()
	{
		return privateSelectHostAutomatically_IsSelected;
	}
	public void setSelectHostAutomatically_IsSelected(EntityModel value)
	{
		privateSelectHostAutomatically_IsSelected = value;
	}

	private EntityModel privateSelectDestinationHost_IsSelected;
	public EntityModel getSelectDestinationHost_IsSelected()
	{
		return privateSelectDestinationHost_IsSelected;
	}
	public void setSelectDestinationHost_IsSelected(EntityModel value)
	{
		privateSelectDestinationHost_IsSelected = value;
	}


	public MigrateModel()
	{
		setHosts(new ListModel());
		getHosts().getSelectedItemChangedEvent().addListener(this);

		setSelectHostAutomatically_IsSelected(new EntityModel());
		getSelectHostAutomatically_IsSelected().getEntityChangedEvent().addListener(this);

		setSelectDestinationHost_IsSelected(new EntityModel());
		getSelectDestinationHost_IsSelected().getEntityChangedEvent().addListener(this);
	}

	@Override
	public void eventRaised(Event ev, Object sender, EventArgs args)
	{
		super.eventRaised(ev, sender, args);
		if (sender == getHosts() && getVmsOnSameCluster())
		{
			VDS selectedHost = (VDS)getHosts().getSelectedItem();
			sethasSameVdsMessage(false);
			for (VM vm : getVmList())
			{
				if(selectedHost.getvds_id().getValue().equals(vm.getrun_on_vds().getValue()))
				{
					sethasSameVdsMessage(true);
					break;
				}
			}
			setIsSameVdsMessageVisible(gethasSameVdsMessage());
		}
		else if (ev.equals(EntityModel.EntityChangedEventDefinition))
		{
			if (sender == getSelectHostAutomatically_IsSelected())
			{
				setIsAutoSelect((Boolean)getSelectHostAutomatically_IsSelected().getEntity());
			}
			else if (sender == getSelectDestinationHost_IsSelected())
			{
				setIsAutoSelect(!(Boolean)getSelectDestinationHost_IsSelected().getEntity());
			}
		}
	}
}