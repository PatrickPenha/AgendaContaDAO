Baixe o instalador do PostgreSQL:

Acesse o site oficial: https://www.postgresql.org/download/windows/
Clique em "Download the installer" e selecione a versão desejada.

Instale o PostgreSQL:

Execute o instalador baixado.
Escolha os componentes: o PostgreSQL, pgAdmin, Stack Builder e Command Line Tools são úteis.
Defina uma senha para o usuário postgres (essa será a senha do superusuário).

Finalize a instalação:

Siga os passos do instalador até o fim.
Ao terminar, abra o pgAdmin ou o Prompt de Comando e conecte-se ao banco usando o comando:
psql -U postgres

Importar o Banco de Dados

Após a instalação, siga estes passos para importar o banco de dados:

Crie o banco de dados:
CREATE DATABASE nome_do_banco;

Importe o arquivo SQL:
psql -U postgres -d nome_do_banco -f caminho/para/banco_inicial.sql