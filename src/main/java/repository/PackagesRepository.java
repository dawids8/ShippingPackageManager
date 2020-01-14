package repository;

import delivery.Package;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PackagesRepository {

    private static final String SELECT_ALL = "SELECT * FROM packages WHERE isDelivered = ?";
    private static final String DELETE_PACKAGE = "DELETE FROM packages WHERE shippingNumber = ?";
    private static final String SET_PACKAGE_STATUS = "UPDATE packages SET isDelivered = ? WHERE shippingNumber = ?";
    private static final String CHECK_SHIPPING_NUMBER = "SELECT * FROM packages WHERE shippingNumber = ?";
    private static final String ADD_NEW_PACKAGE = "INSERT INTO packages " +
            "(shippingNumber, company, address, customer, content, weight, price, shippingDate, isDelivered) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String MODIFY_PACKAGE = "UPDATE packages " +
            "SET shippingNumber = ?, company = ?, address = ?, customer = ?, content = ?, weight = ?, price = ?, shippingDate = ?, isDelivered = ?" +
            " WHERE shippingNumber = ?";


    public static List<Package> getAllPackages(boolean status) {
        try {
            final PreparedStatement preparedStatement = Database.getConnection().prepareStatement(SELECT_ALL);
            preparedStatement.setBoolean(1, status);

            return getListOfPackages(preparedStatement, status);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Package> getListOfPackages(PreparedStatement preparedStatement, boolean status) {
        List<Package> listOfPackages = new ArrayList<>();

        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Package consignment = new Package(resultSet.getString("shippingNumber"),
                        resultSet.getString("company"),
                        resultSet.getString("address"),
                        resultSet.getString("customer"),
                        resultSet.getString("content"),
                        resultSet.getDouble("weight"),
                        resultSet.getDouble("price"),
                        resultSet.getDate("shippingDate").toLocalDate(),
                        status);

                listOfPackages.add(consignment);
            }

            return listOfPackages;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deletePackage(String shippingNumber) {
        try {
            PreparedStatement preparedStatement = Database.getConnection().prepareStatement(DELETE_PACKAGE);
            preparedStatement.setString(1, shippingNumber);

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setPackageStatus(String shippingNumber, boolean status) {
        try {
            PreparedStatement preparedStatement = Database.getConnection().prepareStatement(SET_PACKAGE_STATUS);
            preparedStatement.setBoolean(1, status);
            preparedStatement.setString(2, shippingNumber);

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addNewPackage(Package consignment) {
        try {
            final PreparedStatement preparedStatement = Database.getConnection().prepareStatement(ADD_NEW_PACKAGE);

            setValues(consignment, preparedStatement);

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void modifyPackage(Package aPackage, String number) {
        try {
            PreparedStatement preparedStatement = Database.getConnection().prepareStatement(MODIFY_PACKAGE);

            preparedStatement.setString(10, number);
            setValues(aPackage, preparedStatement);


            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void setValues(Package consignment, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, consignment.getShippingNumber());
        preparedStatement.setString(2, consignment.getDeliveryCompany());
        preparedStatement.setString(3, consignment.getAddress());
        preparedStatement.setString(4, consignment.getCustomer());
        preparedStatement.setString(5, consignment.getContent());
        preparedStatement.setDouble(6, consignment.getWeight());
        preparedStatement.setDouble(7, consignment.getPrice());
        preparedStatement.setDate(8, Date.valueOf(consignment.getShippingDate()));
        preparedStatement.setBoolean(9, consignment.isIsDelivered());
    }

    public static boolean checkIfShippingNumberExist(String number) {
        try {
            final PreparedStatement preparedStatement = Database.getConnection().prepareStatement(CHECK_SHIPPING_NUMBER);
            preparedStatement.setString(1, number);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String specifiedNumber = resultSet.getString("shippingNumber");
                if (number.equals(specifiedNumber)) {
                    return false;
                }
            }

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return true;
    }
}
