package main.java.com.gdcontacts;

import main.java.com.gdcontacts.model.Contact;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Essa é a classe principal do programa, é nela que haverá a execução de toda a aplicação. O objetivo
 * dessa classe é simular um aplicativo onde o usuário poderá adicionar seus contatos, buscar seus contatos,
 * ou remover seus contatos. Toda a parte gráfica do programa foi feita apenas se utilizando de métodos
 * estáticos da classe JOptionPane.
 *
 * @author João Guedes.
 */
public class GDContactsApplication {

    private final List<Contact> CONTACTS;

    public GDContactsApplication() {
        this.CONTACTS = new ArrayList<>();
    }

    /**
     * Inicia um ‘loop’ que gerencia os painéis da aplicação conforme a escolha do usuário. O ‘loop’
     * só será encerrado caso o usuário deseje sair da aplicação.
     */
    public void start() {
        Integer option;
        do {
            option = this.showCentralOptionsDialog();
            if (option == null) {
                return;
            }
            if (option == 3) {
                this.showAddContactDialog();
            }
            else if (this.CONTACTS.isEmpty()) {
                JOptionPane.showMessageDialog(null, "     Lista de contatos vazia.", null, JOptionPane.INFORMATION_MESSAGE);
            }
            else if (option == 1) {
                this.showContactSearchDialog();
            }
            else if (option == 2) {
                this.showContactsListDialog();
            }
            else if (option == 4) {
                this.showEditContactDialog();
            }
            else if (option == 5) {
                this.showRemoveContactDialog();
            }
            else if (option == 6) {
                this.showRemoveAllContactsDialog();
            }
        } while (true);
    }

    /**
     * Exibe um painel central de opções onde o usuário poderá escolher que opção ele deseja usar
     * (buscar contato, visualizar contatos, adicionar contato, remover contato...).
     *
     * @return Retorna um valor equivalente à opção que o usuário escolheu.
     */
    private Integer showCentralOptionsDialog() {
        String[] options = this.getArrayOptions();
        String input;
        int intInput;
        do {
            input = JOptionPane.showInputDialog(null, options, "Menu de Opções ⚙", JOptionPane.PLAIN_MESSAGE);
            if (input == null) {
                return null;
            }
            try {
                intInput = Integer.parseInt(input);
                if (intInput >= 1 && intInput <= options.length) {
                    return intInput;
                }
                JOptionPane.showMessageDialog(null, "          Opção Inválida!", null, JOptionPane.ERROR_MESSAGE);
            }
            catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "          Opção Inválida!", null, JOptionPane.ERROR_MESSAGE);
            }
        } while (true);
    }

    /**
     * Exibe um painel onde o usuário poderá adicionar um contato a sua lista de contatos, informando nome,
     * telefone e email.
     */
    private void showAddContactDialog() {
        Contact newContact = new Contact();
        do {
            newContact = this.showInputContactInformationDialog(newContact.getName(), newContact.getTelephone(), newContact.getEmail());
            if (newContact == null) {
                return;
            }
            if (newContact.getName().length() == 0 && newContact.getTelephone().length() == 0 && newContact.getEmail().length() == 0) {
                JOptionPane.showMessageDialog(null, "Não há nada para salvar. O contato foi descartado.", null, JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (!this.contactAlreadyExists(newContact)) {
                this.CONTACTS.add(newContact);
                this.CONTACTS.sort(Contact::compareTo);
                JOptionPane.showMessageDialog(null, "Contato adicionado com sucesso!", null, JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(null, "Já existe um contato com essa mesma identificação.", null, JOptionPane.ERROR_MESSAGE);
        } while (true);
    }

    /**
     * Exibe um painel onde o usuário poderá visualizar os dados de um contato específico.
     */
    private void showContactSearchDialog() {
        Contact contactSearched = this.showSearchDialog();
        if (contactSearched != null) {
            JOptionPane.showMessageDialog(null, contactSearched, "Contato", JOptionPane.PLAIN_MESSAGE);
        }
    }

    /**
     * Exibe um painel onde o usuário poderá visualizar todos os seus contatos.
     */
    private void showContactsListDialog() {
        String[] contactsInformation = this.CONTACTS.stream().map(Contact::toString).toList().toArray(new String[0]);
        String[] visibleInformation = Arrays.copyOf(contactsInformation, 4);
        String[] options = {"Cancelar", "⏪", "⏩"};
        int length = visibleInformation.length;
        int option, srcPos, destPos = visibleInformation.length-1;
        do {
            option = JOptionPane.showOptionDialog(null, visibleInformation, "Visualizar Contatos 📑", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
            if (option < 1) {
                return;
            }
            if (option == 1 && destPos > (length-1)) {
                srcPos = destPos - (length*2-1);
                destPos -= length;
                System.arraycopy(contactsInformation, srcPos, visibleInformation, 0, length);
            }
            else if (option == 2 && destPos < contactsInformation.length-1) {
                Arrays.fill(visibleInformation, null);
                srcPos = destPos + 1;
                destPos += length;
                System.arraycopy(contactsInformation, srcPos, visibleInformation, 0, Math.min(contactsInformation.length-srcPos, length));
            }
        } while (true);
    }

    /**
     * Exibe um painel onde o usuário poderá editar um contato específico.
     */
    private void showEditContactDialog() {
        Contact contactSearched = this.showSearchDialog();
        if (contactSearched == null) {
            return;
        }
        Contact editedContact;
        do {
            editedContact = this.showInputContactInformationDialog(contactSearched.getName(), contactSearched.getTelephone(), contactSearched.getEmail());
            if (editedContact == null) {
                return;
            }
            if (editedContact.getName().length() == 0 && editedContact.getTelephone().length() == 0 && editedContact.getEmail().length() == 0) {
                JOptionPane.showMessageDialog(null, "Campos vazios. Digite algo ou cancele a edição.", null, JOptionPane.ERROR_MESSAGE);
            }
            else {
                if (!this.editedContactAlreadyExists(contactSearched, editedContact)) {
                    this.CONTACTS.set(this.CONTACTS.indexOf(contactSearched), editedContact);
                    this.CONTACTS.sort(Contact::compareTo);
                    JOptionPane.showMessageDialog(null, "Contato editado com sucesso!", null, JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                JOptionPane.showMessageDialog(null, "Já existe um contato com a mesma identificação!", null, JOptionPane.ERROR_MESSAGE);
            }
        } while(true);
    }

    /**
     * Exibe um painel onde o usuário poderá remover um contato em específico.
     */
    private void showRemoveContactDialog() {
        Contact contactSearched = this.showSearchDialog();
        if (contactSearched == null) {
            return;
        }
        String message = contactSearched + "\nTem certeza que deseja excluir este contato?";
        int option = JOptionPane.showConfirmDialog(null, message, "Excluir Contato ❌",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (option == 0) {
            this.CONTACTS.remove(contactSearched);
            JOptionPane.showMessageDialog(null, "Contato removido com sucesso!", null, JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(null, "O contato não foi excluído.", null, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Exibe um painel onde o usuário poderá remover todos os contatos da sua lista de contatos.
     */
    private void showRemoveAllContactsDialog() {
        int option = JOptionPane.showConfirmDialog(null, "Você tem certeza que quer excluir todos os seus contatos?", "Excluir todos os contatos ⚠", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (option == 0) {
            this.CONTACTS.clear();
            JOptionPane.showMessageDialog(null, "Contatos excluídos com sucesso!", null, JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(null, "Seus contatos não foram excluídos!", null, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Exibe um painel onde o usuário poderá fazer a busca de um contato.
     *
     * @return Retorna um contato. Caso o contato não seja encontrado será retornado o valor null.
     */
    private Contact showSearchDialog() {
        String contactId = this.showInputDialog("Contato: ", "Buscar Contato 🔎", null);
        if (contactId == null) {
            return null;
        }
        Contact contactSearched = this.findContactById(contactId);
        if (contactSearched == null) {
            JOptionPane.showMessageDialog(null, "Contato não encontrado.", null, JOptionPane.INFORMATION_MESSAGE);
        }
        return contactSearched;
    }

    /**
     * Exibe um painel de entrada de dados onde o usuário irá preencher uma informação do contato.
     *
     * @param message Tipo da informação que o usuário irá passar. Ex.: nome, telefone, email...
     *
     * @param title Título do painel.
     *
     * @param initialValue Valor inicial do campo de texto.
     *
     * @return Retorna o valor que o usuário passou de entrada. Caso ele não passe nada o valor
     * retornado será null.
     */
    private String showInputDialog(String message, String title, String initialValue) {
        String input = (String) JOptionPane.showInputDialog(null, message, title, JOptionPane.PLAIN_MESSAGE, null, null, initialValue);
        return input == null ? null : input.trim();
    }

    /**
     * Exibe painéis de entrada de dados para preenchimento das informações de um contato.
     *
     * @param nameInitialValue Valor inicial do campo nome do contato.
     *
     * @param telephoneInitialValue Valor inicial do campo telephone do contato.
     *
     * @param emailInitialValue Valor inicial do campo email do contato.
     *
     * @return Retorna um contato conforme o que o usuário digitou. Caso ele cancele ou não digite
     * nada será retornado o valor null.
     */
    private Contact showInputContactInformationDialog(String nameInitialValue, String telephoneInitialValue, String emailInitialValue) {
        String name = this.showInputDialog("Nome", "Contato ☎", nameInitialValue);
        if (name == null) {
            return null;
        }
        String telephone = this.showInputDialog("Telefone", "Contato ☎", telephoneInitialValue);
        if (telephone == null) {
            return null;
        }
        String email = this.showInputDialog("Email", "Contato ☎",  emailInitialValue);
        if (email == null) {
            return null;
        }
        return this.createContact(name, telephone, email);
    }

    /**
     * Retorna um array de String onde cada posição equivale a uma opção da central de opções.
     *
     * @return Retorna um array de String onde cada posição equivale a uma opção da central de opções.
     */
    private String[] getArrayOptions() {
        return new String[] {
                "[1] - Buscar Contato 🔎",
                "[2] - Visualizar Contatos 👁",
                "[3] - Adicionar Contato ➕",
                "[4] - Editar Contato 🖍",
                "[5] - Excluir Contato ❌",
                "[6] - Excluir Todos os contatos ⚠"
        };
    }

    /**
     * Cria um contato. Caso o nome do contato seja diferente de vazio, o id do contato será igual
     * ao nome. Caso o nome do contato seja vazio e o telefone diferente de vazio, então o id do
     * contato será igual ao telefone. Caso o nome e o telefone sejam vazios, então o id do contato
     * será igual ao email.
     *
     * @param name Nome do contato.
     *
     * @param telephone Telefone do contato.
     *
     * @param email Email do contato.
     *
     * @return Um contato.
     */
    private Contact createContact(String name, String telephone, String email) {
        if (name.length() > 0) {
            return Contact.builder().id(name).name(name).telephone(telephone).email(email).build();
        }
        if (telephone.length() > 0) {
            return Contact.builder().id(telephone).name(name).telephone(telephone).email(email).build();
        }
        return Contact.builder().id(email).name(name).telephone(telephone).email(email).build();
    }

    /**
     * Busca um contato na lista de contatos pelo id.
     *
     * @param contactId id do contato procurado.
     *
     * @return Retorna um contato. Caso o contato não seja encontrado o valor retornado será null.
     */
    private Contact findContactById(String contactId) {
        return this.CONTACTS
                .stream()
                .filter(contact -> contact.getId().equalsIgnoreCase(contactId))
                .findAny()
                .orElse(null);
    }

    /**
     * Diz se um contato com uma mesma identificação já existe na lista de contatos.
     *
     * @param contact Contato que será avaliado.
     *
     * @return true caso um contato com a mesma identificação já esteja contido na lista de contatos
     * ou false caso não exista nem um contato com a mesma identificação.
     */
    private boolean contactAlreadyExists(Contact contact) {
        return this.findContactById(contact.getId()) != null;

    }

    /**
     * Retorna se um contato editado já existe na lista de contatos.
     *
     * @param contact Contato antes da edição.
     *
     * @param editedContact Contato após a edição.
     *
     * @return Retorna true caso o contato editado já exista ou false caso o contato editado não
     * exista na lista de contatos.
     */
    private boolean editedContactAlreadyExists(Contact contact, Contact editedContact) {
        return this.CONTACTS.stream()
                .anyMatch(cont -> cont.getId().equalsIgnoreCase(editedContact.getId())
                        && !(cont.equals(contact)));
    }

    public static void main(String[] args) {
        GDContactsApplication contactsApp = new GDContactsApplication();
        contactsApp.start();
    }

}