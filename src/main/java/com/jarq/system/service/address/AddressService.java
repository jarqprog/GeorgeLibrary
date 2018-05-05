package com.jarq.system.service.address;

import com.jarq.system.exceptions.DaoFailure;
import com.jarq.system.log.ILog;
import com.jarq.system.models.address.IAddress;
import com.jarq.system.models.address.IDaoAddress;
import com.jarq.system.models.user.IDaoUser;
import com.jarq.system.models.user.IUser;
import com.jarq.system.policy.IAddressPolicy;
import com.jarq.system.service.Service;

public class AddressService extends Service implements IAddressService {

    private final IDaoAddress daoAddress;
    private final IDaoUser daoUser;
    private final IAddressPolicy addressPolicy;
    private final String serviceFailure = "Something goes wrong with address operation.";

    public static IAddressService getInstance(ILog log, IDaoAddress daoAddress,
                                              IDaoUser daoUser,
                                              IAddressPolicy addressPolicy) {
        return new AddressService(log, daoAddress, daoUser, addressPolicy);
    }

    private AddressService(ILog log, IDaoAddress daoAddress,
                           IDaoUser daoUser, IAddressPolicy addressPolicy) {
        super(log);
        this.daoAddress = daoAddress;
        this.daoUser = daoUser;
        this.addressPolicy = addressPolicy;
    }


    @Override
    public String createAddress(int userId, String postalCode, String city, String street, String houseNo, String apartmentNo) {
        try {
            if(! addressPolicy.validatePostalCode(postalCode) ) {
                return serviceFailure + "Postal code isn't valid! Operation stopped.";
            }

            IUser user = daoUser.importUser(userId);
            daoAddress.removeAddressByUserId(userId);
            IAddress address;

            if(apartmentNo.length() == 0) {
                address = daoAddress.createAddress(user, postalCode, city, street, houseNo);

            } else {
                address = daoAddress.createAddress(user, postalCode, city, street, houseNo, apartmentNo);
            }
            return address.toString(); // todo

        } catch (DaoFailure daoFailure) {
            reportException(daoFailure);
            return serviceFailure;
        }
    }

    @Override
    public String changePostalCode(int userId, String postalCode) {
        if(! addressPolicy.validatePostalCode(postalCode) ) {
            return serviceFailure + "Postal code isn't valid! Operation stopped.";
        }
        try {
            IAddress address = daoAddress.importAddressByUserId(userId);
            address.setPostalCode(postalCode);
            return updateAddress(address); // todo

        } catch (DaoFailure daoFailure) {
            reportException(daoFailure);
            return serviceFailure;
        }
    }

    @Override
    public String changeCity(int userId, String city) {
        try {
            IAddress address = daoAddress.importAddressByUserId(userId);
            address.setCity(city);
            return updateAddress(address); // todo

        } catch (DaoFailure daoFailure) {
            reportException(daoFailure);
            return serviceFailure;
        }
    }

    @Override
    public String changeStreet(int userId, String street) {
        try {
            IAddress address = daoAddress.importAddressByUserId(userId);
            address.setStreet(street);
            return updateAddress(address); // todo

        } catch (DaoFailure daoFailure) {
            reportException(daoFailure);
            return serviceFailure;
        }
    }

    @Override
    public String changeHouseNo(int userId, String houseNo) {
        try {
            IAddress address = daoAddress.importAddressByUserId(userId);
            address.setHouseNo(houseNo);
            return updateAddress(address); // todo

        } catch (DaoFailure daoFailure) {
            reportException(daoFailure);
            return serviceFailure;
        }
    }

    @Override
    public String changeApartmentNo(int userId, String apartmentNo) {
        try {
            IAddress address = daoAddress.importAddressByUserId(userId);
            address.setApartmentNo(apartmentNo);
            return updateAddress(address); // todo

        } catch (DaoFailure daoFailure) {
            reportException(daoFailure);
            return serviceFailure;
        }
    }

    @Override
    public String removeAddress(int userId) {
        try {
            IAddress address = daoAddress.importAddressByUserId(userId);
            if ( daoAddress.removeAddress(address) ) {
                return address.toString(); // todo
            }
            String message = String.format("%s Problem occurred while deleting address for user id: %s",
                    serviceFailure, userId);
            report(message);
            return message;

        } catch (DaoFailure daoFailure) {
            reportException(daoFailure);
            return serviceFailure;
        }
    }

    private String updateAddress(IAddress address) throws DaoFailure {
        if ( daoAddress.updateAddress(address) ) {
            return address.toString();
        }
        String message = String.format("%s Problem occurred while updating address for user id: %s",
                serviceFailure, address.getUserId());
        report(message);
        return message;
    }

}
