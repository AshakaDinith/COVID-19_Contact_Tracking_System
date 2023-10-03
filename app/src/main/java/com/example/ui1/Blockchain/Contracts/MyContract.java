package com.example.ui1.Blockchain.Contracts;

import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Int256;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.6.0.
 */
public class MyContract extends Contract {
    private static final String BINARY = "606060405234610000575b61051f806100196000396000f30060606040523615610076576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063019ae7ff1461007b578063a0f26c72146100a1578063a4a1e26314610113578063a9011c5714610136578063e166f64d14610167578063ebc3eee81461019c575b610000565b346100005761009f600480803590602001909190803590602001909190505061020e565b005b34610000576100ae6103b6565b6040518080602001828103825283818151815260200191508051906020019060200280838360008314610100575b805182526020831115610100576020820191506020810190506020830392506100dc565b5050509050019250505060405180910390f35b346100005761012061041e565b6040518082815260200191505060405180910390f35b3461000057610151600480803590602001909190505061042c565b6040518082815260200191505060405180910390f35b3461000057610182600480803590602001909190505061044d565b604051808215151515815260200191505060405180910390f35b34610000576101a961048b565b60405180806020018281038252838181518152602001915080519060200190602002808383600083146101fb575b8051825260208311156101fb576020820191506020810190506020830392506101d7565b5050509050019250505060405180910390f35b60406040519081016040528060008152602001600081525060006102318461044d565b1515610314578382600001818152505082826020018181525050600180548060010182818154818355818115116102945781836000526020600020918201910161029391905b8082111561028f576000816000905550600101610277565b5090565b5b505050916000526020600020900160005b8690919091505550600280548060010182818154818355818115116102f6578183600052602060002091820191016102f591905b808211156102f15760008160009055506001016102d9565b5090565b5b505050916000526020600020900160005b8590919091505550610379565b600090505b6001805490508110156103785783600182815481101561000057906000526020600020900160005b5054141561036a5782600282815481101561000057906000526020600020900160005b50819055505b5b8080600101915050610319565b5b8360006000868152602001908152602001600020600001819055508260006000868152602001908152602001600020600101819055505b50505050565b6020604051908101604052806000815250600280548060200260200160405190810160405280929190818152602001828054801561041357602002820191906000526020600020905b8154815260200190600101908083116103ff575b505050505090505b90565b600060018054905090505b90565b6000600060008381526020019081526020016000206001015490505b919050565b6000600161045a8361042c565b148061046e5750600261046c8361042c565b145b1561047c5760019050610486565b60009050610486565b5b919050565b602060405190810160405280600081525060018054806020026020016040519081016040528092919081815260200182805480156104e857602002820191906000526020600020905b8154815260200190600101908083116104d4575b505050505090505b905600a165627a7a72305820a29249b0b218ae0d8a488819f5b394d4f3d893139d2340c36d4fe0bfafe1739e0029";

    public static final String FUNC_ADDUSER = "addUser";

    public static final String FUNC_GETUSERADDRESS = "getUserAddress";

    public static final String FUNC_GETUSERHEALTHSTATUS = "getUserHealthStatus";

    public static final String FUNC_GETUSERSCOUNT = "getUsersCount";

    public static final String FUNC_INARRAY = "inArray";

    @Deprecated
    protected MyContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected MyContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected MyContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected MyContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> addUser(BigInteger _macAddress, BigInteger _healthStatus) {
        final Function function = new Function(
                FUNC_ADDUSER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Int256(_macAddress), 
                new org.web3j.abi.datatypes.generated.Int256(_healthStatus)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<List> getUserAddress() {
        final Function function = new Function(FUNC_GETUSERADDRESS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Int256>>() {}));
        return new RemoteCall<List>(
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteCall<List> getUserHealthStatus() {
        final Function function = new Function(FUNC_GETUSERHEALTHSTATUS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Int256>>() {}));
        return new RemoteCall<List>(
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteCall<BigInteger> getUserHealthStatus(BigInteger _macAddress) {
        final Function function = new Function(FUNC_GETUSERHEALTHSTATUS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Int256(_macAddress)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> getUsersCount() {
        final Function function = new Function(FUNC_GETUSERSCOUNT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<Boolean> inArray(BigInteger _macAddress) {
        final Function function = new Function(FUNC_INARRAY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Int256(_macAddress)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public static RemoteCall<MyContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(MyContract.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<MyContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(MyContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<MyContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(MyContract.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<MyContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(MyContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static MyContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new MyContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static MyContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new MyContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static MyContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new MyContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static MyContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new MyContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }
}
