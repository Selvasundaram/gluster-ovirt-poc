package org.ovirt.engine.core.common.queries;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "IsVmTemlateWithSameNameExistParameters")
public class IsVmTemlateWithSameNameExistParameters extends VdcQueryParametersBase {
    private static final long serialVersionUID = -6485460471694366238L;

    public IsVmTemlateWithSameNameExistParameters(String name) {
        _name = name;
    }

    // C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to
    // .NET attributes:
    @XmlElement(name = "Name")
    private String _name;

    public String getName() {
        return _name;
    }

    @Override
    public RegisterableQueryReturnDataType GetReturnedDataTypeByVdcQueryType(VdcQueryType queryType) {
        return RegisterableQueryReturnDataType.UNDEFINED;
    }

    public IsVmTemlateWithSameNameExistParameters() {
    }
}
