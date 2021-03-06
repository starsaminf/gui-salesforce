package org.fundacionjala.salesforce.ui.context;

import org.fundacionjala.salesforce.ui.entities.Account;
import org.fundacionjala.salesforce.ui.entities.EntitiesParser;
import org.fundacionjala.salesforce.ui.entities.Opportunity;
import org.fundacionjala.salesforce.ui.entities.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * [MR] Context class to share between Step Definitions.
 */
public class Context {

    private List<User> usersList;
    private Account account;
    private List<Account> accountList;
    private Opportunity opportunity;

    /**
     * Constructor for Context class.
     */
    public Context() {
        this.usersList = new ArrayList<>();
        accountList = new ArrayList<>();
    }

    private void setUsersListFromJson() throws IOException {
        this.usersList = EntitiesParser.getUsersListFromJson();
        this.account = null;
    }

    /**
     * Searches for a specific User in userList by a provided alias.
     *
     * @param alias provided alias
     * @return User if the alias matches, otherwise return null.
     */
    public User getUserByAlias(final String alias) throws IOException {
        if (this.usersList.size() == 0) {
            setUsersListFromJson();
        }
        for (User user : this.usersList) {
            if (alias.equals(user.getAlias())) {
                return user;
            }
        }
        return null;
    }

    /**
     * Set Context's Account to share into StepDef classes.
     *
     * @param contextAccount
     */
    public void setAccount(final Account contextAccount) {
        this.account = contextAccount;
    }

    /**
     * Gets Context's Account.
     *
     * @return account
     */
    public Account getAccount() {
        return this.account;
    }

    /**
     * [SL] Gets Context's accountIds.
     *
     * @return account
     */
    public List<Account> getAccountList() {
        return accountList;
    }

    /**
     * Set accountIds.
     *
     * @param contextAccountIdList list with all account ids
     */
    public void setAccountList(final List<Account> contextAccountIdList) {
        accountList.addAll(contextAccountIdList);
    }

    /**
     * Set Context's Opportunity to share into StepDef classes.
     *
     * @param contextOpportunity
     */
    public void setOpportunity(final Opportunity contextOpportunity) {
        this.opportunity = contextOpportunity;
    }

    /**
     * Gets Context's Opportunity.
     *
     * @return opportunity
     */
    public Opportunity getOpportunity() {
        return opportunity;
    }
}
