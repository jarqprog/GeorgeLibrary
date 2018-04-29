package com.jarq.system.models.address;

import java.sql.SQLException;

public interface IDaoAddress {

    IAddress createNullAddress();
    IAddress createAddress() throws SQLException, ;
    IAddress importAddress();
    boolean updateAddress(IAddress address);


}
