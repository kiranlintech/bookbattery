/*
	Author(s)		: Ajay K
	Date			: March 21, 2013
	Copyright Notice: NGIT Pvt.Ltd. Confidential.
	Description		: Retailer Registration.
*/
/*Declaration of the Array list for the cities*/

var cities = new Array();
var cityList = new Array();
cities['Andaman and Nicobar'] = new Array('<-----select city----->', 'Port Blair');
cities['Andhra Pradesh'] = new Array('<-----select city----->', 'Adoni', 'Amadalavalasa', 'Amalapuram', 'Anakapalle', 'Anantapur', 'Badvel', 'Banganapalle', 'Bapatla', 'Bethamcherla', 'Bheemunipatnam', 'Bhimavaram', 'Bobbili', 'Chapirevula', 'Chilakaluripet', 'Chirala', 'Chittoor', 'Cuddapah', 'Dharmavaram', 'Dhone', 'Eluru', 'Farooqnagar', 'Gudivada', 'Gudur', 'Guntakal', 'Guntur', 'Hindupur', 'Ichchapuram', 'Jaggaiahpet', 'Jammalamadugu', 'Kadiri', 'Kakinada', 'Kandukur', 'Kavali', 'Koratla', 'Kovvur', 'Krishna', 'Kurnool', 'Macherla', 'Machilipatnam', 'Madanapalle', 'Mandamarri', 'Mandapeta', 'Mangalagiri', 'Markapur', 'Medak', 'Nagari', 'Nandyal', 'Narasapur', 'Narasaraopet', 'Narsipatnam', 'Nellore', 'Nidadavole', 'Nuzvid', 'Ongole', 'Palacole', 'Palamaner', 'Palasa Kasibugga', 'Parvathipuram', 'Pedana', 'Peddapuram', 'Piduguralla', 'Pithapuram', 'Ponnur', 'Prakasam', 'Proddatur', 'Pulivendla', 'Punganur', 'Puttaparthi', 'Puttur-AP', 'Rajahmundry', 'Rajam', 'Rajampet', 'Ramachandrapuram', 'Rayachoti', 'Rayadurg', 'Repalle', 'Salur', 'Samalkot', 'Sattenapalle', 'Srikakulam', 'Srikalahasti', 'Tadepalle', 'Tadepalligudem', 'Tadpatri', 'Tanuku', 'Tenali', 'Tirupati', 'Tuni', 'Venkatagiri', 'Vijayawada', 'Vinukonda', 'Visakhapatnam', 'Vizianagaram', 'Yemmiganur');
cities['Arunachal Pradesh'] = new Array('<-----select city----->', 'Aalo', 'Anini', 'Anjaw', 'Basar', 'Boleng', 'Bomdila', 'Changlang', 'Daporijo', 'Deomali', 'Dibang Valley', 'Dirang', 'East Kameng', 'Hawai', 'Itanagar', 'Jairampur', 'Khonsa', 'Koloriang', 'Lohit', 'Longding', 'Lower Subansiri', 'Miao', 'Naharlagun', 'Namsai', 'Papum Pare', 'Pasighat', 'Roing', 'Sagalee', 'Seppa', 'Tawang', 'Tezu', 'Tirap', 'Upper Subansiri', 'West Kameng', 'Yingkiong', 'Ziro');
cities['Assam'] = new Array('<-----select city----->', 'Abhayapuri', 'Amguri', 'Badarpur', 'Barpathar', 'Barpeta', 'Barpeta Road', 'Basugaon', 'Bihpuria', 'Bijni', 'Bilasipara', 'Biswanath Chariali', 'Bokajan', 'Bokakhat', 'Bongaigaon', 'Cachar', 'Chabua', 'Chapar', 'Darrang', 'Dergaon', 'Dhakuakhana', 'Dhekiajuli', 'Dhemaji', 'Dhing', 'Dhubri', 'Dibrugarh', 'Digboi', 'Diphu', 'Doboka', 'Dokmoka', 'Donkamokam', 'Doom Dooma', 'Gauripur', 'Goalpara', 'Gohpur', 'Golaghat', 'Guwahati', 'Haflong', 'Hailakandi', 'Hamren', 'Hojai', 'Howli', 'Howraghat', 'Jorhat', 'Kampur Town', 'Karbi Anglong', 'Karimganj', 'Kharupatia', 'Kokrajhar', 'Lakhimpur-Assam', 'Lakhipur', 'Lala', 'Lanka', 'Lumding', 'Mahur', 'Maibong', 'Makum', 'Mangaldoi', 'Margherita', 'Mariani', 'Marigaon', 'Moranhat', 'Nagaon', 'Naharkatiya', 'Nalbari', 'Narayanpur', 'Nazira', 'North Cachar Hills', 'North Guwahati', 'North Lakhimpur', 'Palasbari', 'Pathsala', 'Raha', 'Rangapara', 'Rangia', 'Sapatgram', 'Sarbhog', 'Sarthebari', 'Sarupathar', 'Sibsagar', 'Silapathar', 'Silchar', 'Simaluguri', 'Sonari', 'Sonitpur', 'Tangla', 'Teok', 'Tezpur', 'Tihu', 'Tinsukia', 'Titabor Town', 'Udalguri', 'Umrangso');
cities['Bihar'] = new Array('<-----select city----->', 'Amarpur', 'Araria', 'Areraj', 'Arrah', 'Arwal', 'Aurangabad-Bihar', 'Bagaha', 'Bahadurganj', 'Bairgania', 'Bakhri', 'Bakhtiarpur', 'Balia', 'Banka', 'Banmankhi Bazar', 'Barahiya', 'Barauli', 'Barauni', 'Barbigha', 'Barh', 'Begusarai', 'Behea', 'Belsand', 'Benipur', 'Bettiah', 'Bhabua', 'Bhagalpur', 'Bhojpur', 'Biharsharif', 'Bihat', 'Bihta', 'Bikram', 'Bikramganj', 'Birpur', 'Bodh Gaya', 'Buxar', 'Chakia', 'Chanpatia', 'Chapra', 'Colgong', 'Dalsinghsarai', 'Darbhanga', 'Daudnagar', 'Dehri', 'Dhaka', 'Dighwara', 'Dinapur Cantonment', 'Dinapur Nizamat', 'Dumra', 'Dumraon', 'Fatwah', 'Forbesganj', 'Gaya', 'Ghoghardiha', 'Gogri Jamalpur', 'Gopalganj', 'Hajipur', 'Hilsa', 'Hisua', 'Islampur', 'Jagdishpur', 'Jainagar', 'Jamalpur', 'Jamshedpur-Bihar', 'Jamui', 'Janakpur Road', 'Jehanabad', 'Jhajha', 'Jhanjharpur', 'Jogabani', 'Kaimur', 'Kanti', 'Kasba', 'Kataiya', 'Katihar', 'Kesaria', 'Khagaria', 'Khagaul', 'Kharagpur', 'Khusrupur', 'Kishanganj', 'Koath', 'Koilwar', 'Lakhisarai', 'Lalganj', 'Madhepura', 'Madhuban', 'Madhubani', 'Maharajganj', 'Mahnar Bazar', 'Mairwa', 'Makhdumpur', 'Maner', 'Manihari', 'Marhaura', 'Masaurhi', 'Mehsi', 'Mirganj', 'Mokameh', 'Motihari', 'Motipur', 'Munger', 'Murliganj', 'Muzaffarpur', 'Nabinagar', 'Nalanda', 'Narkatiaganj', 'Nasriganj', 'Naubatpur', 'Naugachhia', 'Nawada', 'Nirmali', 'Nokha', 'Pakri Dayal', 'Pashchim Champaran', 'Patna', 'Phulwari Sharif', 'Piro', 'Purba Champaran', 'Purnia', 'Rafiganj', 'Rajauli', 'Rajgir', 'Raxual', 'Revelganj', 'Rohtas', 'Rosera', 'Saharsa', 'Sahebganj', 'Samastipur', 'Saran', 'Sasaram', 'Shahpur', 'Sheikhpura', 'Sheohar', 'Sherghati', 'Silao', 'Sitamarhi', 'Siwan', 'Sonepur', 'Sugauli', 'Sultanganj', 'Supaul', 'Teghra', 'Thakurganj', 'Tikari', 'Vaishali', 'Warisaliganj');
cities['Chattisgarh'] = new Array('<-----select city----->', 'Aamadi', 'Abhanpur', 'Adbhar', 'Ahiwara', 'Akaltara', 'Ambagarh Chowki', 'Ambikapur', 'Antagarh', 'Arang', 'Arjunda', 'Bade Bacheli', 'Bagbahara', 'Bagicha', 'Baikunthpur', 'Balod', 'Baloda', 'Baloda Bazar', 'Balrampur', 'Baramkela', 'Barsur', 'Basna', 'Bastar', 'Bemetara', 'Berla', 'Bhairamgarh', 'Bhakhara', 'Bhanupratappur', 'Bhatapara', 'Bhatgaon', 'Bhilai', 'Bhilai Charoda', 'Bhilai Nagar', 'Bhopalpattanam', 'Bijapur-Ctgh', 'Bilaigarh', 'Bilaspur', 'Bilha', 'Birgaon', 'Bishrampur', 'Bodla', 'Bodri', 'Champa', 'Chandrapur-Ctgh', 'Charama', 'Chhuikhadan', 'Chhura', 'Chhurikala', 'Chhuriya', 'Chikhalakasa', 'Chirmiri', 'Dabhra', 'Dalli Rajhara', 'Dantewada', 'Daundi Lohara', 'Devkar', 'Dhamdha', 'Dhamtari', 'Dharamjaigarh', 'Dipka', 'Dongargaon', 'Dongargarh', 'Dornapal', 'Doundi', 'Durg', 'Farasgaon', 'Fingeshwar', 'Gandai', 'Gariyaband', 'Gaurella', 'Geedam', 'Gharghoda', 'Gobra Nawapara', 'Gunderdehi', 'Gurur', 'Jagdalpur', 'Jaijepur', 'Jamul', 'Janjgir Champa', 'Jarhi', 'Jashpur', 'JashpurNagar', 'Jhagrakhand', 'Kanker', 'Kasdol', 'Katghora', 'Kawardha', 'Keskal', 'Khairagarh', 'Kharod', 'Kharora', 'Kharsia', 'Khongapani', 'Kirandul', 'Kirodimalnagar', 'Kondagaon', 'Konta', 'Koora', 'Korba', 'Koriya', 'Kota', 'Kotba', 'Kumhari', 'Kurud', 'Kusmi', 'Lailunga', 'Lakhanpur', 'Lawan', 'Lormi', 'Magarlod', 'Mahasamund', 'Malhar', 'Mana Camp', 'Manendragarh', 'Maro', 'Mungeli', 'Nagari', 'Naila Janjgir', 'Nai Ledri', 'Narayanpur', 'Narharpur', 'Nawagarh', 'Naya Baradwar', 'Pakhanjur', 'Palari', 'Pali', 'Pandariya', 'Pandatarai', 'Parpondi', 'Patan', 'Pathalgaon', 'Pathariya', 'Pendra', 'Pipariya', 'Pithora', 'Pratappur', 'Premnagar', 'Pusaur', 'Rahaud', 'Raigarh', 'Raipur', 'Rajim', 'Rajnandgaon', 'Rajpur', 'Ramanujganj', 'Ratanpur', 'Sahaspur Lohara', 'Saja', 'Sakari', 'Sakti', 'Saragaon', 'Saraipali', 'Sarangarh', 'Sargaon', 'Sariya', 'Shivrinarayan', 'Simga', 'Sirgitti', 'Sitapur', 'Sukma', 'Surajpur', 'Surguja', 'Takhatpur', 'Than Khamharia', 'Tifra', 'Tilda Newra', 'Tumgaon', 'Tundra', 'Utai', 'Visrampuree', 'Wadrafnagar');
cities['Delhi'] = new Array('<-----select city----->', 'Central Delhi', 'East Delhi', 'New Delhi', 'North Delhi', 'North East Delhi', 'North West Delhi', 'South Delhi', 'South West Delhi', 'West Delhi');
cities['Goa'] = new Array('<-----select city----->', 'Aldona', 'Bicholim', 'Canacona', 'Cuncolim', 'Curchorem Cacora', 'Mapusa', 'Margao', 'Mormugao', 'Panaji', 'Pernem', 'Ponda', 'Quepem', 'Sanguem', 'Sanquelim', 'Valpoi');
cities['Gujarat'] = new Array('<-----select city----->','Adalaj', 'Adityana', 'Ahmedabad', 'Ahmedabad Cantonment', 'Alang Sosiya', 'Ambaji', 'Ambaliyasan', 'Amod', 'Amreli', 'Anand', 'Anjar', 'Anklav', 'Anklesvar', 'Antaliya', 'Arambhada', 'Atul', 'Babra', 'Bagasara', 'Balasinor', 'Banaskantha', 'Bantwa', 'Bardoli', 'Bareja', 'Baroda', 'Barwala', 'Bavla', 'Bayad', 'Bhabhar', 'Bhachau', 'Bhanvad', 'Bharuch', 'Bhavnagar', 'Bhayavadar', 'Bhuj', 'Bilimora', 'Boriavi', 'Borsad', 'Botad', 'Cambay', 'Chaklasi', 'Chalala', 'Chanasma', 'Chhatral', 'Chhaya', 'Chhota Udaipur', 'Chorvad', 'Chotila', 'Dabhoi', 'Dahod', 'Dakor', 'Damnagar', 'Deesa', 'Dehgam', 'Devgadbaria', 'Dhandhuka', 'Dhanera', 'Dharampur', 'Dholka', 'Dhoraji', 'Dhrangadhra', 'Dhrol', 'Dohad', 'Dwarka', 'Gadhada', 'Gandevi', 'Gandhidham', 'Gandhinagar', 'Gariadhar', 'Godhra', 'Gondal', 'GSFC', 'GSFC Complex', 'Hajira', 'Halol', 'Halvad', 'Harij', 'Himatnagar', 'Idar', 'Jafrabad', 'Jambusar', 'Jamjodhpur', 'Jamnagar', 'Jasdan', 'Jetpur Navagadh', 'Jhalod', 'Junagadh', 'Kadi', 'Kalavad', 'Kalol', 'Kanjari', 'Kansad', 'Kapadvanj', 'Karamsad', 'Karjan', 'Kathlal', 'Keshod', 'Khambhalia', 'Khambhat', 'Kheda', 'Khedbrahma', 'Kheralu', 'Kodinar', 'Kutch', 'Kutiyana', 'Lathi', 'Limbdi', 'Lodhika', 'Lunawada', 'Magdalla', 'Mahesana', 'Mahudha', 'Mahuva', 'Maliya', 'Manavadar', 'Mandvi', 'Mangrol', 'Mansa', 'Mehmedabad', 'Mehsana', 'Modasa', 'Morvi', 'Nadiad', 'Nandesari', 'Narmada', 'Navsari', 'Ode', 'Okha', 'Padra', 'Palanpur', 'Palitana', 'Panchmahal', 'Panoli', 'Pardi', 'Patan', 'Patdi', 'Pethapur', 'Petlad', 'Petro Chemical Complex', 'Porbandar', 'Por Ramangamdi', 'Prantij', 'Radhanpur', 'Rajkot', 'Rajpipla', 'Rajula', 'Ranavav', 'Rapar', 'Raval', 'Reliance Complex', 'Sabarkantha', 'Sachin', 'Salaya', 'Sanand', 'Santrampur', 'Saputara', 'Sarigam', 'Savarkundla', 'Savli', 'Shehera', 'Sidhpur', 'Sihor', 'Sikka', 'Sojitra', 'Songadh', 'Surat', 'Surendranagar', 'Surendranagar Dudhrej', 'Sutrapada', 'Talaja', 'Talala', 'Talod', 'Tarsadi', 'Thangadh', 'Thara', 'Tharad', 'Thasra', 'The Dangs', 'Umbergaon', 'Umreth', 'Una', 'Unjha', 'Upleta', 'Vadali', 'Vadnagar', 'Vadodara', 'Vaghodia', 'Valia', 'Valia Jhagadia', 'Vallabh Vidyanagar', 'Vallabhipur', 'Valsad', 'Vanthali', 'Vapi', 'Vasna Borsad', 'Veraval', 'Vijalpor', 'Vijapur', 'Viramgam', 'Visavadar', 'Visnagar', 'Vithal Udyognagar', 'Vyara', 'Wadhwan', 'Wankaner');
cities['Haryana'] = new Array('<-----select city----->', 'Ambala', 'Ambala Cantonment', 'Ambala Sadar', 'Asankhurd', 'Assandh', 'Ateli', 'Babiyal', 'Bahadurgarh', 'Ballabhgarh', 'Barwala', 'Bawal', 'Bawani khera', 'Beri', 'Bhiwani', 'Charkhi Dadri', 'Cheeka', 'Dharuhera', 'Ellenabad', 'Faridabad', 'Farrukhnagar', 'Fatehabad', 'Ferozepur jhirka', 'Ganaur', 'Gharaunda', 'Gohana', 'Gurgaon', 'Hailey Mandi', 'Hansi', 'Hassan Pur', 'Hathin', 'Hisar', 'Hodal', 'Indri', 'Jagadhri', 'Jhajjar', 'Jind', 'Julana', 'Kaithal', 'Kalanaur', 'Kalanwali', 'Kalayat', 'Kalka', 'Kanina', 'Karnal', 'Kharkhoda', 'Kurukshetra', 'Ladwa', 'Loharu', 'Maham', 'Mahendragarh', 'Mandi Dabwali', 'Mewat', 'Naraingarh', 'Narnaul', 'Narnaund', 'Narwana', 'Nilokheri', 'Nissing', 'Nuh', 'Palwal', 'Panchkula', 'Panipat', 'Pataudi', 'Pehowa', 'Pinjore', 'Punahana', 'Pundri', 'Rania', 'Ratia', 'Rewari', 'Rohtak', 'Safidon', 'Samalkha', 'Sampla', 'Shahbad', 'Sirsa', 'Siwani', 'Sohna', 'Sonepat', 'Taoru', 'Taraori', 'Thanesar', 'Tohana', 'Uchana', 'Yamunanagar');
cities['Himachal Pradesh'] = new Array('<-----select city----->', 'Arki', 'Baddi', 'Bakloh', 'Banjar', 'Bhota', 'Bhuntar', 'Bilaspur-HP', 'Chamba', 'Chaupal', 'Chuari Khas', 'Dagshai', 'Dalhousie', 'Daulatpur', 'Dera Gopipur', 'Dharmsala', 'Gagret', 'Ghumarwin', 'Hamirpur', 'Jawalamukhi', 'Jogindarnagar', 'Jubbal', 'Jutogh', 'Kangra', 'Kasauli', 'Kinnaur', 'Kotkhai', 'Kullu', 'Lahaul and Spiti', 'Manali', 'Mandi', 'Mehatpur Basdehra', 'Nadaun', 'Nagrota Bagwan', 'Nahan', 'Naina Devi', 'Nalagarh', 'Narkanda', 'Nurpur', 'Palampur', 'Paonta Sahib', 'Parwanoo', 'Rajgarh', 'Rampur', 'Rawalsar', 'Rohru', 'Sabathu', 'Santokhgarh', 'Sarkaghat', 'Seoni', 'Shimla', 'Sirmaur', 'Solan', 'Sundarnagar', 'Talai', 'Theog', 'Tira Sujanpur', 'Una', 'Yol');
cities['Jammu and Kashmir'] = new Array('<-----select city----->','Achhabal', 'Aishmuquam', 'Akhnoor', 'Anantnag', 'Arnia', 'Arwani', 'Ashmuji Khalsa', 'Awantipora', 'Badami Bagh', 'Badgam', 'Bandipore', 'Banihal', 'Baramula', 'Bari Brahamana', 'Bashohli', 'Batote', 'Beerwah', 'Bhaderwah', 'Bijbehara', 'Billawar', 'Bishna', 'Chadura', 'Charari Sharief', 'Chenani', 'Devsar', 'Doda', 'Duru Verinag', 'Frisal', 'Ganderbal', 'Ghomanhasan', 'Gulmarg', 'Hajan', 'Handwara', 'Hiranagar', 'Jammu', 'Jammu Cantonment', 'Jourian', 'Kargil', 'Kathua', 'Katra', 'Khansahib', 'Khore', 'Khrew', 'Kishtwar', 'Koker Nag', 'Kud', 'Kulgam', 'Kunzer', 'Kupwara', 'Lakhanpur', 'Leh', 'Magam', 'Mattan', 'Mehmood Pora', 'Nowshehra', 'Pahalgam', 'Pampora', 'Parole', 'Pattan', 'Poonch', 'Pulwama', 'Purana Daroorh', 'Qazi Gund', 'Quimoh', 'RS Pora', 'Rajauri', 'Ram Nagar', 'Ramban', 'Ramgarh', 'Reasi', 'Samba', 'Seer Hamdan', 'Shangus', 'Shupiyan', 'Sopore', 'Srinagar', 'Sumbal', 'Sunderbani', 'Surankote', 'Thanamandi', 'Tral', 'Udhampur', 'Uri', 'Vijay Pur', 'Watra Gam', 'Yari Pora');
cities['Jharkhand'] = new Array('<-----select city----->', 'Adityapur', 'Amlabad', 'Ara', 'Barughutu', 'Basukinath', 'Bishrampur', 'Bokaro', 'Bundu', 'Chaibasa', 'Chakardharpur', 'Chakulia', 'Chas', 'Chatra', 'Chirkunda', 'Deoghar', 'Dhanbad', 'Dumka', 'Garhwa', 'Giridih', 'Godda', 'Gumla', 'Hazaribag', 'Hussainabad', 'Jamshedpur-Jharkhand', 'Jamtara', 'Jhumri Tilaiya', 'Jugsalai', 'Khunti', 'Kodarma', 'Latehar', 'Lohardaga', 'Madhupur', 'Majhion', 'Mango', 'Medininagar', 'Mihijam', 'Pakaur', 'Palamu', 'Pashchim Singhbhum', 'Phusro', 'Purba Singhbhum', 'Rajmahal', 'Ramgarh', 'Ramgarh Cantonment', 'Ranchi', 'Sahibganj', 'Seraikela', 'Simdega');
cities['Karnataka'] = new Array('<-----select city----->','Afzalpur', 'Aland', 'Alnavar', 'Alur', 'Anekal', 'Ankola', 'Annigeri', 'Arkalgud', 'Arsikere', 'Athni', 'Aurad', 'Badami', 'Bagalkot', 'Bagepalli', 'Bail Hongal', 'Bangalore', 'Bangarapet', 'Bankapura', 'Bannur', 'Bantval', 'Basavakalyan', 'Basavana Bagevadi', 'BBMP', 'Belgaum', 'Belgaum Cantonment', 'Bellary', 'Beltangadi', 'Belur', 'Bhadravati', 'Bhalki', 'Bhatkal', 'Bhimarayanagudi', 'Bidar', 'Bijapur', 'Bilgi', 'Birur', 'Byadgi', 'Challakere', 'Chamarajanagar', 'Channagiri', 'Channapatna', 'Channarayapatna', 'Chikkaballapura', 'Chikmagalur', 'Chiknayakanhalli', 'Chikodi', 'Chincholi', 'Chintamani', 'Chitapur', 'Chitgoppa', 'Chitradurga', 'Dandeli', 'Darwad', 'Davanagere', 'Devadurga', 'Devanahalli', 'Dod Ballapur', 'Gadag', 'Gajendragarh', 'Gangawati', 'Gauribidanur', 'Gokak', 'Gokak Falls', 'Gubbi', 'Gudibanda', 'Gulbarga', 'Guledgudda', 'Gundlupet', 'Gurmatkal', 'Haliyal', 'Hampi', 'Hangal', 'Hanur', 'Harapanahalli', 'Harihar', 'Hassan', 'Hatti Gold Mines', 'Haveri', 'Heggadadevankote', 'Hirekerur', 'Hiriyur', 'Holalkere', 'Hole Narsipur', 'Homnabad', 'Honavar', 'Honnali', 'Hoovina Hadagalli', 'Hosakote', 'Hosanagara', 'Hosdurga', 'Hospet', 'Hukeri', 'Hungund', 'Hunsur', 'Ilkal', 'Indi', 'Jagalur', 'Jamkhandi', 'Jevargi', 'Jog Kargal', 'Kadur', 'Kalghatgi', 'Kamalapuram', 'Kampli', 'Kanakapura', 'Karkal', 'Karwar', 'Kerur', 'Khanapur', 'Kolar', 'Kollegal', 'Konnur', 'Koppa', 'Koppal', 'Koratagere', 'Kotturu', 'Krishnarajanagara', 'Krishnarajpet', 'Kudchi', 'Kudligi', 'Kudremukh', 'Kumta', 'Kundapura', 'Kundgol', 'Kunigal', 'Kushalnagar', 'Kushtagi', 'Lakshmeshwar', 'Lingsugur', 'Maddur', 'Madhugiri', 'Madikeri', 'Magadi', 'Mahalingpur', 'Malavalli', 'Malur', 'Mandya', 'Mangalore', 'Manipal', 'Manvi', 'Molakalmuru', 'Mudalgi', 'Mudbidri', 'Muddebihal', 'Mudgal', 'Mudhol', 'Mudigere', 'Mulbagal', 'Mulgund', 'Mulki', 'Mundargi', 'Mundgod', 'Mysore', 'Nagamangala', 'Nanjangud', 'Narasimharajapura', 'Naregal', 'Nargund', 'Navalgund', 'Nelamangala', 'Nipani', 'Pandavapura', 'Pavagada', 'Piriyapatna', 'Puttur-Karnataka', 'Rabkavi Banhatti', 'Raichur', 'Ramanagara', 'Ramdurg', 'Ranibennur', 'Raybag', 'Robertson Pet', 'Ron', 'Sadalgi', 'Sagar-Bglr', 'Sakleshpur', 'Saligram', 'Sandur', 'Sankeshwar', 'Saragur', 'Saundatti Yellamma', 'Savanur', 'Sedam', 'Shahabad', 'Shahabad ACC', 'Shahpur', 'Shiggaon', 'Shikarpur', 'Shimoga', 'Shirhatti', 'Shorapur', 'Siddapur', 'Sidlaghatta', 'Sindgi', 'Sindhnur', 'Sira', 'Siralkoppa', 'Sirsi', 'Siruguppa', 'Somvarpet', 'Sorab', 'Sringeri', 'Srinivaspur', 'Srirangapatna', 'Sulya', 'Talikota', 'Tarikere', 'Tekkalakote', 'Terdal', 'Tiptur', 'Tirthahalli', 'Tirumakudal Narsipur', 'Tumkur', 'Turuvekere', 'Udupi', 'Ullal', 'Vijayapura', 'Virajpet', 'Wadi', 'Yadgir', 'Yelandur', 'Yelbarga', 'Yellapur');
cities['Kerala'] = new Array('<-----select city----->', 'Adoor', 'Akathiyoor', 'Alappuzha', 'Alleppey', 'Aluva', 'Alwaye', 'Ancharakandy', 'Angamaly', 'Aroor', 'Ashtamichira', 'Attingal', 'Avinissery','Calicut', 'Chalakudy', 'Changanassery', 'Chavakkad', 'Chendamangalam', 'Chengannur', 'Cherthala', 'Cheruthazham', 'Chockli', 'Erattupetta', 'Ernakulam', 'Guruvayoor', 'Haripad', 'Idukki', 'Irinjalakuda', 'Kadirur', 'Kalamassery', 'Kalliasseri', 'Kalpetta', 'Kanhangad', 'Kanjikkuzhi', 'Kannur', 'Kannur Cantonment', 'Kasaragod', 'Kayamkulam', 'Kochi', 'Kodungallur', 'Kollam', 'Koothuparamba', 'Kothamangalam', 'Kottayam', 'Kovalam', 'Kozhikode', 'Kunnamkulam', 'Malappuram', 'Manjeri', 'Mattannur', 'Mavelikkara', 'Mavoor', 'Muvattupuzha', 'Nedumangad', 'Neyyattinkara', 'Nilambur', 'Ottappalam', 'Palai', 'Palakkad', 'Panamattom', 'Panniyannur', 'Pappinisseri', 'Paravoor', 'Paravur', 'Pathanamthitta', 'Payyannur', 'Peringathur', 'Perinthalmanna', 'Perumbavoor', 'Ponkunnam', 'Ponnani', 'Punalur', 'Puthuppally', 'Quilandy', 'Shoranur', 'Taliparamba', 'Thalassery','Thiruvalla', 'Thiruvananthapuram', 'Thodupuzha', 'Thrippunithura', 'Thrissur','Tirur', 'Trichur', 'Trivandrum', 'Vadakara', 'Vaikom', 'Varkala', 'Wayanad');
cities['Madhya Pradesh'] = new Array('<-----select city----->', 'Agar', 'Ajaigarh', 'Akoda', 'Akodia', 'Alampur', 'Alirajpur', 'Alot', 'Amanganj', 'Amarkantak', 'Amarpatan', 'Amarwara', 'Ambah', 'Amla', 'Anjad', 'Antari', 'Anuppur', 'Aron', 'Ashoknagar', 'Ashta', 'Athner', 'Babai', 'Bada Malhera', 'Badagaon', 'Badarwas', 'Badawada', 'Badi', 'Badkuhi', 'Badnagar', 'Badnawar', 'Badod', 'Badoni', 'Bagli', 'Baihar', 'Baikunthpur', 'Balaghat', 'Baldeogarh', 'Bamhani', 'Bamor', 'Banda', 'Baraily', 'Barela', 'Barghat', 'Barhi', 'Barigarh', 'Barwaha', 'Barwani', 'Basoda', 'Begamganj', 'Beohari', 'Berasia', 'Betma', 'Betul', 'Betul Bazar', 'Bhainsdehi', 'Bhander', 'Bhanpura', 'Bhaurasa', 'Bhavra', 'Bhedaghat', 'Bhikangaon', 'Bhind', 'Bhitarwar', 'Bhopal', 'Biaora', 'Bichhiya', 'Bijawar', 'Bijuri', 'Bilaua', 'Bina Etawa', 'Birsinghpur', 'Boda', 'Budni', 'Burhanpur', 'Burhar', 'Buxwaha', 'Chachaura Binaganj', 'Chakghat', 'Chandameta Butaria', 'Chanderi', 'Chandia', 'Chandla', 'Chaurai Khas', 'Chhanera', 'Chhapiheda', 'Chhatarpur', 'Chhindwara', 'Chicholi', 'Chitrakoot', 'Churhat', 'Daboh', 'Dabra', 'Dahi', 'Damoh', 'Damua', 'Datia', 'Deori', 'Depalpur', 'Devendranagar', 'Dewas', 'Dhamnod', 'Dhanpuri', 'Dhar', 'Dharampuri', 'Diken', 'Dindori', 'Dongar Parasia', 'Gadarwara', 'Gairatganj', 'Garhakota', 'Garhi Malhera', 'Garoth', 'Ghuwara', 'Gohad', 'Gormi', 'Gotegaon', 'Govindgarh', 'Guna', 'Gurh', 'Gwalior', 'Hanumana', 'Harda', 'Harpalpur', 'Harrai', 'Hatod', 'Hatpiplya', 'Hatta', 'Hindoria', 'Hoshangabad', 'Ichhawar', 'Indergarh', 'Indore', 'Isagarh', 'Itarsi', 'Jabalpur', 'Jabalpur Cantt', 'Jagdalpur', 'Jaisinghnagar', 'Jaithari', 'Jaitwara', 'Jamai', 'Jaora', 'Jatara', 'Jawad', 'Jawar', 'Jeron Khalsa', 'Jhabua', 'Jhundpura', 'Jiran', 'Jirapur', 'Jobat', 'Joura', 'Kailaras', 'Kakarhati', 'Kanad', 'Kannod', 'Kantaphod', 'Kareli', 'Karera', 'Kari', 'Karnawad', 'Kasrawad', 'Katangi', 'Katni', 'Khacharod', 'Khajuraho', 'Khand', 'Khandwa', 'Khaniyadhana', 'Khargapur', 'Khargone', 'Khategaon', 'Khetia', 'Khilchipur', 'Khirkiya', 'Khujner', 'Khurai', 'Kolar', 'Kolaras', 'Kotar', 'Kothi', 'Kothri', 'Kotma', 'Kukdeshwar', 'Kukshi', 'Kumbhraj', 'Kurwai', 'Kymore', 'Lahar', 'Lakhnadon', 'Lanji', 'Lateri', 'Laundi', 'Lidhora Khas', 'Lodhikheda', 'Loharda', 'Machalpur', 'Maharajpur', 'Maheshwar', 'Mahidpur', 'Maihar', 'Majhauli', 'Majholi', 'Makdon', 'Maksi', 'Malajkhand', 'Malhargarh', 'Manasa', 'Manawar', 'Mandav', 'Mandideep', 'Mandla', 'Mandleshwar', 'Mandsaur', 'Mangawan', 'Manpur', 'Mau', 'Mauganj', 'Mehgaon', 'Mhow Cantt', 'Mhowgaon', 'Mihona', 'Mohgaon', 'Morar Cantt', 'Morena', 'Multai', 'Mundi', 'Mungaoli', 'Murwara', 'Nagda', 'Nagod', 'Nagri', 'Naigarhi', 'Nainpur', 'Nalkheda', 'Namli', 'Narayangarh', 'Narsimhapur', 'Narsinghgarh', 'Narsinghpur', 'Narwar', 'Nasrullaganj', 'Neemuch', 'Nepanagar', 'Neuton Chikhli Kalan', 'Niwari', 'Niwas', 'Nowgong', 'Nowrozabad', 'Obedullaganj', 'Omkareshwar', 'Orchha', 'Pachmarhi Cantt', 'Pachore', 'Palera', 'Pali', 'Palsud', 'Panagar', 'Pandhana', 'Pandhurna', 'Panna', 'Pansemal', 'Pasan', 'Patan', 'Patharia', 'Pawai', 'Petlawad', 'Phuphkalan', 'Pichhore', 'Pipalrawan', 'Pipariya', 'Piplanarayanwar', 'Piploda', 'Piplya Mandi', 'Pithampur', 'Polaykalan', 'Porsa', 'Prithvipur', 'Raghogarh Vijaypur', 'Rahatgarh', 'Raisen', 'Rajgarh', 'Rajnagar', 'Rajpur', 'Rampur Baghelan', 'Rampur Naikin', 'Rampura', 'Ranapur', 'Ratangarh', 'Ratlam', 'Rau', 'Rehli', 'Rehti', 'Rewa', 'Runji Gautampura', 'Sabalgarh', 'Sagar', 'Sagar Cantt', 'Sailana', 'Sanawad', 'Sanchi', 'Sarangpur', 'Sardarpur', 'Sarni', 'Satai', 'Satna', 'Satwas', 'Sausar', 'Sawer', 'Sehore', 'Semaria', 'Sendhwa', 'Seondha', 'Seoni', 'Seoni Malwa', 'Shahdol', 'Shahgarh', 'Shahpur', 'Shahpura', 'Shajapur', 'Shamgarh', 'Shamshabad', 'Sheopur', 'Shivpuri', 'Shujalpur', 'Sidhi', 'Sihora', 'Silwani', 'Singoli', 'Singrauli', 'Sirmour', 'Sironj', 'Sitamau', 'Sohagpur', 'Sonkatch', 'Soyatkalan', 'Sultanpur', 'Susner', 'Suthaliya', 'Suwasara', 'Tal', 'Talen', 'Tarana', 'Tarichar Kalan', 'Tendukheda', 'Teonthar', 'Thandla', 'Tikamgarh', 'Timarni', 'Tonk Khurd', 'Udaipura', 'Ujjain', 'Umaria', 'Unchahara', 'Unhel', 'Vidisha', 'Vijaypur', 'Vijayraghavgarh', 'Waraseoni');
cities['Maharashtra'] = new Array('<-----select city----->', 'Achalpur', 'Ahmadpur', 'Ahmednagar', 'Akkalkot', 'Akola', 'Akot', 'Alandi', 'Alibag', 'Amalner', 'Ambad', 'Ambarnath', 'Ambejogai', 'Amravati', 'Anjangaon', 'Arvi', 'Ashta', 'Aurangabad', 'Ausa', 'Badlapur', 'Balapur', 'Ballarpur', 'Baramati', 'Barshi', 'Basmath', 'Beed', 'Bhadgaon', 'Bhadravati', 'Bhagur', 'Bhandara', 'Bhiwandi Nizampur', 'Bhokar', 'Bhokardan', 'Bhor', 'Bhum', 'Bhusawal', 'Bid', 'Biloli', 'Brahmapuri', 'Buldhana', 'Chalisgaon', 'Chandrapur', 'Chandur Railway', 'Chandurbazar', 'Chikhaldara', 'Chikhli', 'Chiplun', 'Chopda', 'Dahanu', 'Dapoli Camp', 'Darwha', 'Daryapur Banosa', 'Dattapur Dhamangaon', 'Daund', 'Deglur', 'Dehu Road', 'Deolali', 'Deolali Pravara', 'Deoli', 'Desaiganj', 'Deulgaon Raja', 'Dharangaon', 'Dharmabad', 'Dharur', 'Dhule', 'Digras', 'Dondaicha Warwade', 'Dudhani', 'Erandol', 'Faizpur', 'Gadchiroli', 'Gadhinglaj', 'Gangakhed', 'Gangapur', 'Georai', 'Ghatanji', 'Gondiya', 'Greater Mumbai', 'Hadgaon', 'Hinganghat', 'Hingoli', 'Ichalkaranji', 'Igatpuri', 'Indapur', 'Jalgaon', 'Jalna', 'Jamner', 'Jawhar', 'Jaysingpur', 'Jejuri', 'Jintur', 'Junnar', 'Kagal', 'Kaij', 'Kalamb', 'Kalameshwar', 'Kalamnuri', 'Kalyan Dombivli', 'Kamptee', 'Kandhar', 'Kankavli', 'Kannad', 'Karad', 'Karanja', 'Karjat', 'Karmala', 'Katol', 'Khamgaon', 'Khapa', 'Khed', 'Khopoli', 'Khuldabad', 'Kinwat', 'Kirkee', 'Kolhapur', 'Kopargaon', 'Kundalwadi', 'Kurduvadi', 'Kurundvad', 'Latur', 'Loha', 'Lonar', 'Lonavala', 'Mahabaleshwar', 'Mahad', 'Maindargi', 'Malegaon', 'Malkapur', 'Malwan', 'Mangalvedhe', 'Mangrulpir', 'Manjlegaon', 'Manmad', 'Manwath', 'Matheran', 'Mehkar', 'Mhaswad', 'Mira Bhayandar', 'Mohpa', 'Morshi', 'Mowad', 'Mudkhed', 'Mukhed', 'Mul', 'Mumbai', 'Murgud', 'Murtijapur', 'Murud Janjira', 'Murum', 'Nagpur', 'Naldurg', 'Nanded', 'Nandgaon', 'Nandura', 'Nandurbar', 'Narkhed', 'Nashik', 'Navi Mumbai', 'Nawapur', 'Ner', 'Nilanga', 'Osmanabad', 'Pachora', 'Paithan', 'Palghar', 'Panchgani', 'Pandharkaoda', 'Pandharpur', 'Panhala', 'Panvel', 'Paranda', 'Parbhani', 'Parli', 'Parola', 'Partur', 'Pathardi', 'Pathri', 'Patur', 'Pauni', 'Pen', 'Peth Umri', 'Phaltan', 'Pimpri Chinchwad', 'Pulgaon', 'Pune', 'Purna', 'Pusad', 'Rahimatpur', 'Rahta Pimplas', 'Rahuri', 'Raigad', 'Rajapur', 'Rajura', 'Ramtek', 'Ratnagiri', 'Raver', 'Risod', 'Roha Ashtami', 'Sailu', 'Sangamner', 'Sangli', 'Sangli Miraj Kupwad', 'Sangole', 'Sasvad', 'Satana', 'Satara', 'Savda', 'Savner', 'Sawantwadi', 'Shahade', 'Shegaon', 'Shendurjana', 'Shirdi', 'Shirpur Warwade', 'Shirur', 'Shrigonda', 'Shrirampur', 'Shrivardhan', 'Sillod', 'Sindhudurg', 'Sindi', 'Sindkhed Raja', 'Sinnar', 'Solapur', 'Sonpeth', 'Talegaon Dabhade', 'Talode', 'Tasgaon', 'Telhara', 'Thane', 'Tirora', 'Trimbak', 'Tuljapur', 'Tumsar', 'Udgir', 'Ulhasnagar', 'Umarga', 'Umarkhed', 'Umred', 'Uran', 'Uran Islampur', 'Vadgaon Kasba', 'Vaijapur', 'Vasai Virar City', 'Vengurla', 'Vita', 'Wai', 'Wani', 'Wardha', 'Warora', 'Warud', 'Washim', 'Yavatmal', 'Yawal', 'Yevla');
cities['Manipur'] = new Array('<-----select city----->','Andro', 'Bishnupur', 'Churachandpur', 'Heirok', 'Imphal', 'Jiribam', 'Kakching', 'Kakching Khunou', 'Kumbi', 'Kwakta', 'Lamlai', 'Lamsang', 'Lilong', 'Mayang Imphal', 'Moirang', 'Moreh', 'Nambol', 'Ningthoukhong', 'Oinam', 'Samurou', 'Samurou NP', 'Sekmai Bazar', 'Senapati', 'Sikhong Sekmai', 'Sugnu', 'Tamenglong', 'Thongkhong Laxmi Bazar', 'Thoubal', 'Ukhrul', 'Wangjing', 'Wangoi', 'Yairipok');
cities['Meghalaya'] = new Array('<-----select city----->','Baghmara', 'Jowai', 'Mairang', 'Nongpoh', 'Nongstoin', 'Resubelpara', 'Shillong', 'Tura', 'Williamnagar');
cities['Mizoram'] = new Array('<-----select city----->', 'Aizawl', 'Bairabi', 'Biate', 'Champhai', 'Darlawn', 'Hnahthial', 'Khawhai', 'Khawzawl', 'Kolasib', 'Lawngtlai', 'Lengpui', 'Lunglei', 'Mamit', 'N Kawnpui', 'North Vanlaiphai', 'Saiha', 'Sairang', 'Saitual', 'Serchhip', 'Thenzawl', 'Tlabung', 'Vairengte', 'Zawlnuam');
cities['Nagaland'] = new Array('<-----select city----->', 'Changtongya', 'Chumukedima', 'Dimapur', 'Jalukie', 'Kiphire', 'Kohima', 'Longleng', 'Medziphema', 'Mokokchung', 'Mon', 'Naginimora', 'Peren', 'Pfutsero', 'Phek', 'Tseminyu', 'Tuensang', 'Tuli', 'Wokha', 'Zunheboto');
cities['Orissa'] = new Array('<-----select city----->', 'Anandpur', 'Anugul', 'Asika', 'Athagad', 'Athmallik', 'Balangir', 'Balasore', 'Baleswar', 'Balimela', 'Balugaon', 'Banapur', 'Banki', 'Barapali', 'Barbil', 'Bargarh', 'Baripada', 'Basudebpur', 'Baudhgarh', 'Bellaguntha', 'Belpahar', 'Berhampur', 'Bhadrak', 'Bhanjanagar', 'Bhawanipatna', 'Bhuban', 'Bhubaneswar', 'Binika', 'Biramitrapur', 'Boudh', 'Brahmapur', 'Brajarajnagar', 'Buguda', 'Burla', 'Byasanagar', 'Chhatrapur', 'Chikiti', 'Choudwar', 'Cuttack', 'Debagarh', 'Dhamanagar', 'Dhenkanal', 'Digapahandi', 'G Udayagiri', 'Gajapati', 'Ganjam', 'Gopalpur', 'Gudari', 'Gunupur', 'Hinjilicut', 'Hirakud', 'Jagatsinghapur', 'Jajapur', 'Jaleshwar', 'Jatani', 'Jeypur', 'Jharsuguda', 'Joda', 'Junagarh', 'Kabisurjyanagar', 'Kalahandi', 'Kamakshyanagar', 'Kandhamal', 'Kantabanji', 'Karanjia', 'Kashinagar', 'Kendrapara', 'Kendujhar', 'Kesinga', 'Khalikote', 'Khandapada', 'Khariar', 'Khariar Road', 'Khordha', 'Kochinda', 'Kodala', 'Konark', 'Koraput', 'Kotpad', 'Malkangiri', 'Mayurbhanj', 'Nabarangapur', 'Nayagarh', 'Nilagiri', 'Nimapada', 'Nuapada', 'OCL', 'Padmapur', 'Paradip', 'Paralakhemundi', 'Patnagarh', 'Pattamundai', 'Phulabani', 'Pipili', 'Polasara', 'Puri', 'Purusottampur', 'Rairangpur', 'Rajagangapur', 'Rambha', 'Rayagada', 'Redhakhol', 'Remuna', 'Rourkela', 'Sambalpur', 'Sonapur', 'Soro', 'Subarnapur', 'Sunabeda', 'Sundargarh', 'Surada', 'Talcher', 'Tarbha', 'Titlagarh', 'Udala', 'Umarkote');
cities['Punjab'] = new Array('<-----select city----->', 'Abohar', 'Adampur', 'Ahmedgarh', 'Ajnala', 'Alawalpur', 'Amloh', 'Amritsar', 'Amritsar Cantonment', 'Anandpur Sahib', 'Badhni Kalan', 'Bagha Purana', 'Balachaur', 'Banga', 'Banur', 'Bareta', 'Bariwala', 'Barnala', 'Bassi Pathana', 'Batala', 'Bathinda', 'Begowal', 'Bhadaur', 'Bhadson', 'Bhagta Bhai Ka', 'Bhatinda', 'Bhawanigarh', 'Bhikhi', 'Bhogpur', 'Bhucho Mandi', 'Bhulath', 'Budhlada', 'Chamkaur Sahib', 'Cheema', 'Dasua', 'Dera Baba Nanak', 'Dera Bassi', 'Dhanaula', 'Dharamkot', 'Dhariwal', 'Dhilwan', 'Dhuri', 'Dina Nagar', 'Dirba', 'Doraha', 'Faridkot', 'Faridkot', 'Fatehgarh Churian', 'Fatehgarh Sahib', 'Fazilka', 'Firozpur', 'Firozpur Cantt', 'Gardhiwala', 'Garhshankar', 'Ghagga', 'Ghanaur', 'Gidderbaha', 'Gobindgarh', 'Goniana', 'Goraya', 'Gurdaspur', 'Guru Har Sahai', 'Handiaya', 'Hariana', 'Hoshiarpur', 'Jagraon', 'Jaitu', 'Jalalabad', 'Jalandhar', 'Jalandhar Cantonment', 'Jandiala', 'Kapurthala', 'Kartarpur', 'Khamanon', 'Khanauri', 'Khanna', 'Kharar', 'Khem Karan', 'Kot Fatta', 'Kot Kapura', 'Kurali', 'Lehragaga', 'Lohian Khass', 'Longowal', 'Ludhiana', 'Machhiwara', 'Mahilpur', 'Majitha', 'Makhu', 'Malerkotla', 'Mallanwala Khass', 'Maloud', 'Malout', 'Mansa', 'Maur', 'Moga', 'Moonak', 'Morinda', 'Mudki', 'Mukatsar', 'Mukerian', 'Mullanpur Dakha', 'Nabha', 'Nakodar', 'Nangal', 'Nawan shehar', 'Naya Gaon', 'Nurmahal', 'Pathankot', 'Patiala', 'Patran', 'Patti', 'Payal', 'Phagwara', 'Phillaur', 'Qadian', 'Rahon', 'Raikot', 'Raja Sansi', 'Rajpura', 'Raman', 'Ramdas', 'Rampura Phul', 'Rayya', 'Rupnagar', 'SAS Nagar', 'Sahnewal', 'Samana', 'Samrala', 'Sanaur', 'Sangat', 'Sangrurh', 'Sardulgarh', 'Shahkot', 'Sham Chaurasi', 'Sirhind Fatehgarh Sahib', 'Sri Hargobindpur', 'Sujanpur', 'Sultanpur', 'Sunam Udham Singh Wala', 'Talwandi Bhai', 'Talwandi Sabo', 'Tapa', 'Tarn Taran', 'Urmar Tanda', 'Zira', 'Zirakpur');
cities['Rajasthan'] = new Array('<-----select city----->','Abu Road', 'Ajmer', 'Aklera', 'Alwar', 'Amet', 'Antah', 'Anupgarh', 'Asind', 'Baggar', 'Bagru', 'Bali', 'Balotra', 'Bandikui', 'Banswara', 'Baran', 'Bari', 'Bari Sadri', 'Barmer', 'Bayana', 'Beawar', 'Begun', 'Behror', 'Bhadra', 'Bharatpur', 'Bhawani Mandi', 'Bhilwara', 'Bhinder', 'Bhinmal', 'Bhiwadi', 'Bhusawar', 'Bidasar', 'Bikaner', 'Bilara', 'Bissau', 'Bundi', 'Chaksu', 'Chhabra', 'Chhapar', 'Chhoti Sadri', 'Chirawa', 'Chittaurgarh', 'Chomu', 'Churu', 'Dausa', 'Deeg', 'Deogarh', 'Deoli', 'Deshnoke', 'Dhoulpur', 'Didwana', 'Dungargarh', 'Dungarpur', 'Falna', 'Fatehnagar', 'Fatehpur', 'Gajsinghpur', 'Ganganagar', 'Gangapur', 'Gangapur City', 'Gulabpura', 'Hanumangarh', 'Hindaun', 'Indragarh', 'Jahazpur', 'Jaipur', 'Jaisalmer', 'Jaitaran', 'Jalore', 'Jhalawar', 'Jhalrapatan', 'Jhunjhunun', 'Jobner', 'Jodhpur', 'Kaithoon', 'Kaman', 'Kanor', 'Kapasan', 'Kaprain', 'Karanpur', 'Karauli', 'Kekri', 'Keshoraipatan', 'Kesrisinghpur', 'Khairthal', 'Khandela', 'Kherli', 'Khetri', 'Kishangarh', 'Kishangarh Renwal', 'Kota', 'Kotputli', 'Kuchaman City', 'Kuchera', 'Kumher', 'Kushalgarh', 'Lachhmangarh', 'Ladnu', 'Lakheri', 'Lalsot', 'Losal', 'Makrana', 'Malpura', 'Mandalgarh', 'Mandawa', 'Mangrol', 'Merta City', 'Mount Abu', 'Mukandgarh', 'Mundwa', 'Nadbai', 'Nagar', 'Nagaur', 'Nainwa', 'Nasirabad', 'Nathdwara', 'Nawa', 'Nawalgarh', 'Neemka Thana', 'Nimbahera', 'Niwai', 'Nohar', 'Nokha', 'Padampur', 'Pali', 'Parbatsar', 'Phalodi', 'Phulera', 'Pilani', 'Pilibanga', 'Pindwara', 'Pipar City', 'Pirawa', 'Pokaran', 'Pratapgarh', 'Pushkar', 'Raisinghnagar', 'Rajakhera', 'Rajaldesar', 'Rajgarh', 'Rajsamand', 'Ramganj Mandi', 'Ramgarh', 'Rani', 'Ratangarh', 'Ratannagar', 'Rawatbhata', 'Rawatsar', 'Reengus', 'Sadri', 'Sadulshahar', 'Sagwara', 'Salumbar', 'Sambhar', 'Sanchore', 'Sangaria', 'Sangod', 'Sardarshahar', 'Sarwar', 'Sawai Madhopur', 'Shahpura', 'Sheoganj', 'Sikar', 'Sirohi', 'Sojat', 'Sri Madhopur', 'Sujangarh', 'Sumerpur', 'Surajgarh', 'Suratgarh', 'Takhatgarh', 'Taranagar', 'Tijara', 'Todabhim', 'Todaraisingh', 'Tonk', 'Udaipur', 'Udaipurwati', 'Uniara', 'Vidyavihar', 'Vijainagar', 'Viratnagar', 'Weir');
cities['Sikkim'] = new Array('<-----select city----->', 'Gangtok', 'Geyzing', 'Gyalshing', 'Jorethang', 'Mangan', 'Namchi', 'Nayabazar Notified Bazar Area', 'Rangpo', 'Singtam');
cities['Tamil Nadu'] = new Array('<-----select city----->','A Vellalapatti', 'Abiramam', 'Achampudur', 'Acharapakkam', 'Adikaratti', 'Adiramapattinam', 'Aduthurai alias Maruthuvakudi', 'Agaram', 'Agastheeswaram', 'Alagappapuram', 'Alampalayam', 'Alandur', 'Alanganallur', 'Alangayam', 'Alangudi', 'Alangulam', 'Alanthurai', 'Allapuram', 'Alur', 'Alwarkurichi', 'Alwarthirunagiri', 'Ambasamudram', 'Ambattur', 'Ambur', 'Ammainaickanur', 'Ammapettai', 'Ammoor', 'Anaimalai', 'Anaiyur', 'Anakaputhur', 'Ananthapuram', 'Andipatti Jakkampatti', 'Anjugrammam', 'Annamalai Nagar', 'Annavasal', 'Annur', 'Anthiyur', 'Appakudal', 'Arachalur', 'Arakandanallur', 'Arakonam', 'Aralvaimozhi', 'Arani', 'Aranthangi', 'Arasiramani', 'Aravakurichi', 'Arcot', 'Arimalam', 'Ariyalur', 'Ariyappampalayam', 'Arumanai', 'Arumbavur', 'Arumuganeri', 'Aruppukkottai', 'Asaripallam', 'Athani', 'Athanur', 'Athur', 'Attayampatti', 'Attur', 'Avadi', 'Avalpoondurai', 'Avanashi', 'Avaniapuram', 'Ayakudi', 'Aygudi', 'Ayothiapattinam', 'Ayyalur', 'Ayyampalayam', 'Ayyampettai', 'Azhagiapandipuram', 'B Meenakshipuram', 'B Mallapuram', 'Balakrishnampatti', 'Balasamudram', 'Bargur', 'Batlagundu', 'Belur', 'Bhavani', 'Bhavanisagar', 'Bhuvanagiri', 'Bikketti', 'Bodinayakanur', 'Boothapandi', 'Boothipuram', 'Brahmana Periya Agraharam', 'Chengalpet', 'Chengam', 'Chennai', 'Chennasamudram', 'Chennimalai', 'Cheranmadevi', 'Chetpet', 'Chettiarpatti', 'Chettipalayam', 'Chidambaram', 'Chinnakkampalayam', 'Chinnalapatti', 'Chinnamanur', 'Chinnasalem', 'Chinnasekkadu', 'Chinnavedampatti', 'Chithode', 'Chitlapakkam', 'Cholapuram', 'Coimbatore', 'Colachel', 'Coonoor', 'Courtallam', 'Cuddalore', 'Denkanikottai', 'Desur', 'Devadanapatti', 'Devakottai', 'Devarshola', 'Dhali', 'Dhaliyur', 'Dharapadavedu', 'Dharapuram', 'Dharasuram', 'Dharmapuri', 'Dindigul', 'Edaganasalai', 'Edaicode', 'Edakalinadu', 'Edappadi', 'Elathur', 'Elumalai', 'Eral', 'Eraniel', 'Eriodu', 'Erode', 'Erumaipatti', 'Eruvadi', 'Ethapur', 'Ettayapuram', 'Ettimadai', 'Ezhudesam', 'Ganapathipuram', 'Gandhinagar', 'Gangaikondan', 'Gangavalli', 'Ganguvarpatti', 'Gingee', 'Gobichettipalayam', 'Gopalasamudram', 'Goundampalayam', 'Gudalur', 'Gudiyatham', 'Gummidipoondi', 'Hanumanthampatti', 'Harur', 'Harveypatti', 'Highways', 'Hosur', 'Huligal', 'Idikarai', 'Ilampillai', 'Ilanji', 'Ilayangudi', 'Iluppur', 'Inam Karur', 'Irugur', 'Jagathala', 'Jalakandapuram', 'Jambai', 'Jayankondam', 'Jolarpet', 'Kadambur', 'Kadathur', 'Kadayal', 'Kadayampatti', 'Kadayanallur', 'Kalakad', 'Kalambur', 'Kalapatti', 'Kalappanaickenpatti', 'Kalavai', 'Kalinjur', 'Kaliyakkavilai', 'Kalladaikurichi', 'Kallakkurichi', 'Kallakudi', 'Kallukuttam', 'Kalugumalai', 'Kamayagoundanpatti', 'Kambainallur', 'Kambam', 'Kamuthi', 'Kanadukathan', 'Kanam', 'Kanchipuram', 'Kandanur', 'Kangeyam', 'Kaniyur', 'Kanjikoil', 'Kannamangalam', 'Kannampalayam', 'Kannankurichi', 'Kannivadi', 'Kanyakumari', 'Kappiyarai', 'Karaikkudi', 'Karamadai', 'Karambakkudi', 'Kariamangalam', 'Kariapatti', 'Karumandi Chellipalayam', 'Karumathampatti', 'Karungal', 'Karunguzhi', 'Karuppur', 'Karur', 'Kasipalayam', 'Katpadi', 'Kattivakkam', 'Kattumannarkoil', 'Kattuputhur', 'Kaveripakkam', 'Kaveripattinam', 'Kayalpattinam', 'Kayatharu', 'Keelakarai', 'Keeramangalam', 'Keeranur', 'Keeripatti', 'Keezhkulam', 'Kelamangalam', 'Kembainaickenpalayam', 'Kethi', 'Kilampadi', 'Kilapavoor', 'Kilkunda', 'Killai', 'Killiyoor', 'Kilpennathur', 'Kilvelur', 'Kinathukadavu', 'Kodaikanal', 'Kodavasal', 'Kodumudi', 'Kolappalur', 'Kolathupalayam', 'Kolathur', 'Kollancode', 'Kollankoil', 'Komaralingam', 'Kombai', 'Konganapuram', 'Koothappar', 'Koradacheri', 'Kotagiri', 'Kothanallur', 'Kottaiyur', 'Kottakuppam', 'Kottaram', 'Kottur', 'Kovilpatti', 'Krishnagiri', 'Krishnarayapuram', 'Kuchanur', 'Kuhalur', 'Kulasekaram', 'Kulithalai', 'Kumarapalayam', 'Kumarapuram', 'Kumbakonam', 'Kundrathur', 'Kuniyamuthur', 'Kunnathur', 'Kurichi', 'Kurinjipadi', 'Kurumbalur', 'Kuthalam', 'Kuthanallur', 'Kuzhithurai', 'Labbaikudikadu', 'Lakkampatti', 'Lalgudi', 'Lalpet', 'Madambakkam', 'Madathukulam', 'Madavaram', 'Madukkarai', 'Madukkur', 'Madurai', 'Maduranthakam', 'Maduravoyal', 'Mallankinaru', 'Mallasamudram', 'Mallur', 'Mamallapuram', 'Mamsapuram', 'Manachanallur', 'Manali', 'Manalmedu', 'Manalurpet', 'Manamadurai', 'Manapparai', 'Manavalakurichi', 'Mandaikadu', 'Mandapam', 'Mangadu', 'Mangalampet', 'Manimutharu', 'Mannargudi', 'Maraimalainagar', 'Marakkanam', 'Marandahalli', 'Markayankottai', 'Marudur', 'Marungur', 'Mathigiri', 'Mayiladuthurai', 'Mecheri', 'Meenambakkam', 'Melacheval', 'Melachokkanathapuram', 'Melagaram', 'Melathiruppanthuruthi', 'Melattur', 'Melpattampakkam', 'Melur', 'Melvisharam', 'Mettupalayam', 'Mettur', 'Minjur', 'Modakurichi', 'Mohanur', 'Moolakaraipatti', 'Mopperipalayam', 'Mudukulathur', 'Mukkudal', 'Mulagumudu', 'Mulanur', 'Musiri', 'Muthupet', 'Muthur', 'Mylaudy', 'Naduvattam', 'Nagapattinam', 'Nagarcoil', 'Nagojanahalli', 'Nallampatti', 'Nalloor', 'Namagiripettai', 'Namakkal', 'Nambiyur', 'Nandambakkam', 'Nandivaram Guduvancheri', 'Nangavalli', 'Nangavaram', 'Nanguneri', 'Nannilam', 'Naranammalpuram', 'Narasimhanaickenpalayam', 'Narasingapuram', 'Naravarikuppam', 'Nasiyanur', 'Natham', 'Natrampalli', 'Nattarasankottai', 'Nazerath', 'Needamangalam', 'Neikkarapatti', 'Nellikuppam', 'Nelliyalam', 'Nemili', 'Nerkuppai', 'Nerunjipettai', 'Neyveli', 'Neyyoor', 'Nilakkottai', 'Nilgiris', 'O Valley', 'Odaipatti', 'Odaiyakulam', 'Oddanchatram', 'Odugathur', 'Olagadam', 'Omalur', 'Ooty', 'Orathanadu', 'Othakalmandapam', 'PJ Cholapuram', 'P Mettupalayam', 'PN Patti', 'Pacode', 'Padaiveedu', 'Padmanabhapuram', 'Palakkodu', 'Palamedu', 'Palani', 'Palani Chettipatti', 'Palappallam', 'Palayam', 'Palladam', 'Pallapalayam', 'Pallapatti', 'Pallathur', 'Pallavaram', 'Pallikaranai', 'Pallikonda', 'Pallipalayam', 'Pallipattu', 'Palugal', 'Pammal', 'Panagudi', 'Panaimarathupatti', 'Panapakkam', 'Pandamangalam', 'Pannaikadu', 'Pannaipuram', 'Panpoli', 'Panruti', 'Papanasam', 'Papparapatti', 'Pappireddipatti', 'Paramakudi', 'Paramathi', 'Parangipettai', 'Paravai', 'Pasur', 'Pathamadai', 'Pattinam', 'Pattiveeranpatti', 'Pattukkottai', 'Peerkankaranai', 'Pennadam', 'Pennagaram', 'Pennathur', 'Peraiyur', 'Peralam', 'Perambalur', 'Peranamallur', 'Peravurani', 'Periya Negamam', 'Periyakodiveri', 'Periyakulam', 'Periyanaickenpalayam', 'Periyasemur', 'Pernampattu', 'Perumagalur', 'Perundurai', 'Perungalathur', 'Perungudi', 'Perungulam', 'Perur', 'Pethampalayam', 'Pethanaickenpalayam', 'Pillanallur', 'Pollachi', 'Polur', 'Ponmanai', 'Ponnamaravathi', 'Ponnampatti', 'Ponneri', 'Poolambadi', 'Poolampatti', 'Pooluvapatti', 'Poonamallee', 'Porur', 'Pothanur', 'Pothatturpettai', 'Pudukkottai', 'Pudupalayam', 'Pudupatti', 'Pudur', 'Puduvayal', 'Puliankudi', 'Puliyur', 'Pullampadi', 'Punjai Thottakurichi', 'Punjaipugalur', 'Punjaipuliampatti', 'Puthalam', 'Puthukkadai', 'Puvalur', 'Puzhal', 'Puzhithivakkam', 'R Pudupatti', 'RS Mangalam', 'Rajapalayam', 'Ramanathapuram', 'Rameswaram', 'Ranipettai', 'Rasipuram', 'Rayagiri', 'Reethapuram', 'Rudravathi', 'S Kannanur', 'S Kodikulam', 'S Nallur', 'Salangapalayam', 'Salem', 'Samalapuram', 'Samathur', 'Sambavar Vadagarai', 'Sankaramanallur', 'Sankarankoil', 'Sankarapuram', 'Sankari', 'Sankarnagar', 'Saravanampatti', 'Sarcarsamakulam', 'Sathankulam', 'Sathuvachari', 'Sathyamangalam', 'Sattur', 'Sayalgudi', 'Sayapuram', 'Seerapalli', 'Seithur', 'Sembakkam', 'Senthamangalam', 'Sentharapatti', 'Sethiathoppu', 'Sevilimedu', 'Sevugampatti', 'Shenbakkam', 'Shenkottai', 'Sholavandan', 'Sholinganallur', 'Sholingur', 'Sholur', 'Singampuneri', 'Sirkali', 'Sirugamani', 'Sirumugai', 'Sithayankottai', 'Sivagangai', 'Sivagiri', 'Sivakasi', 'Srimushnam', 'Sriperumbudur', 'Sriramapuram', 'Srivaikuntam', 'Srivilliputhur', 'StThomas Mount', 'Suchindrum', 'Suleeswaranpatti', 'Sulur', 'Sundarapandiam', 'Sundarapandiapuram', 'Surampatti', 'Surandai', 'Suriyampalayam', 'Swamimalai', 'T Kallupatti', 'Tambaram', 'Tenkasi', 'Thadikombu', 'Thakkolam', 'Thalainayar', 'Thamaraikulam', 'Thammampatti', 'Thanjavur', 'Thanthoni', 'Tharamangalam', 'Tharangambadi', 'Thathaiyangarpet', 'Thazhakudy', 'Thedavur', 'Thengampudur', 'Theni', 'Thenkarai', 'Thenthamaraikulam', 'Thenthiruperai', 'Therur', 'Thevaram', 'Thevur', 'Thiagadurgam', 'Thingalnagar', 'Thirparappu', 'Thirukarungudi', 'Thirukkattupalli', 'Thirumalayampalayam', 'Thirumangalam', 'Thirumazhisai', 'Thirumuruganpoondi', 'Thirunagar', 'Thirunageswaram', 'Thiruneermalai', 'Thirunindravur', 'Thiruparankundram', 'Thiruporur', 'Thiruppanandal', 'Thirupuvanam', 'Thiruthangal', 'Thiruthuraipoondi', 'Thiruvaiyaru', 'Thiruvalam', 'Thiruvallur', 'Thiruvarur', 'Thiruvattar', 'Thiruvenkadam', 'Thiruvennainallur', 'Thiruverumbur', 'Thiruvidaimarudur', 'Thiruvithancode', 'Thisayanvilai', 'Thondamuthur', 'Thondi', 'Thoothukkudi', 'Thorapadi', 'Thottiyam', 'Thudiyalur', 'Thuraiyur', 'Thuvakudi', 'Timiri', 'Tindivanam', 'Tiruchendur', 'Tiruchengode', 'Tiruchirappalli', 'Tirukalukundram', 'Tirukkoyilur', 'Tirunelveli', 'Tirupathur', 'Tiruppur', 'Tiruttani', 'Tiruvannamalai', 'Tiruverkadu', 'Tiruvethipuram', 'Tiruvottiyur', 'Tittacheri', 'Tittakudi', 'TNPL Pugalur', 'Trichy', 'Tuticorin', 'Udangudi', 'Udayarpalayam', 'Udhagamandalam', 'Udumalaipettai', 'Ulundurpettai', 'Unjalur', 'Unnamalaikadai', 'Uppidamangalam', 'Uppiliapuram', 'Usilampatti', 'Uthamapalayam', 'Uthangarai', 'Uthayendram', 'Uthiramerur', 'Uthukkottai', 'Uthukuli', 'V Pudur', 'V Pudupatti', 'Vadakarai Keezhpadugai', 'Vadakkanandal', 'Vadakkuvalliyur', 'Vadalur', 'Vadamadurai', 'Vadavalli', 'Vadipatti', 'Vadugapatti', 'Vaitheeswarankoil', 'Valangaiman', 'Valasaravakkam', 'Valavanur', 'Vallam', 'Valparai', 'Valvaithankoshtam', 'Vanavasi', 'Vandavasi', 'Vaniputhur', 'Vaniyambadi', 'Varadarajanpettai', 'Vasudevanallur', 'Vathirairuppu', 'Vazhapadi', 'Vedapatti', 'Vedaranyam', 'Vedasandur', 'Veeraganur', 'Veerakeralam', 'Veerakkalpudur', 'Veerapandi', 'Veerappanchatiram', 'Veeravanallur', 'Velampalayam', 'Velankanni', 'Vellakinar', 'Vellakoil', 'Vellalur', 'Vellimalai', 'Vellore', 'Vellottamparappu', 'Vengampudur', 'Venkarai', 'Vennanthur', 'Veppathur', 'Verkilambi', 'Vettaikaranpudur', 'Vettavalam', 'Vikramasingapuram', 'Vikravandi', 'Vilangudi', 'Vilapakkam', 'Vilathikulam', 'Vilavur', 'Villukuri', 'Viluppuram', 'Virudhachalam', 'Virudhunagar', 'Walajabad', 'Walajapet', 'Wellington', 'Yercaud', 'Zamin Uthukuli');
cities['Telangana'] = new Array('<-----select city----->', 'Adilabad', 'Armur', 'Bellampalle', 'Bhadrachalam', 'Bhainsa', 'Bhongir', 'Bodhan', 'Devarakonda', 'Gadwal', 'Hyderabad', 'Jagtial', 'Jangaon', 'Kagaznagar', 'Kamareddy', 'Karimnagar', 'Khammam', 'Kothagudem', 'Mahbubnagar', 'Mancherial', 'Manugur', 'Medak', 'Metpally', 'Miryalaguda', 'Nalgonda', 'Narayanpet', 'Nirmal', 'Nizamabad', 'Palwancha', 'Ramagundam', 'Sadasivpet', 'Sangareddy', 'Sathupalli', 'Secunderabad', 'Siddipet', 'Sircilla', 'Suryapet', 'Tandur', 'Vicarabad', 'Wanaparthy', 'Warangal', 'Yellandu', 'Zahirabad');
cities['Tripura'] = new Array('<-----select city----->', 'Agartala', 'Amarpur', 'Ambassa', 'Belonia', 'Bishalgarh', 'Dharmanagar', 'Kailasahar', 'Kamalpur', 'Khowai', 'Kumarghat', 'Ranirbazar', 'Sabroom', 'Santir Bazar', 'Sonamura', 'Teliamura', 'Udaipur-Trpr');
cities['Union Territories'] = new Array('<-----select city----->', 'Andaman & Nicobar', 'Chandigarh', 'Dadra and Nagar Haveli', 'Daman and Diu', 'Lakshadweep', 'National Capital Territory of Delhi', 'Puducherry');
cities['Uttarakhand'] = new Array('<-----select city----->', 'Almora', 'Badrinathpuri', 'Bageshwar', 'Barkot', 'Bazpur', 'BHEL Ranipur', 'Bhimtal', 'Bhowali', 'Chakrata', 'Chamba', 'Chamoli', 'Champawat', 'Clement Town', 'Dehradun', 'Devaprayag', 'Dharchula', 'Didihat', 'Dineshpur', 'Dogadda', 'Doiwala', 'Dwarahat', 'Gadarpur', 'Gangotri', 'Gochar', 'Haldwani', 'Haridwar', 'Herbertpur', 'Jaspur', 'Jhabrera', 'Joshimath', 'Kaladhungi', 'Karnaprayag', 'Kashipur', 'Kedarnath', 'Kela Khera', 'Khatima', 'Kichha', 'Kirtinagar', 'Kotdwara', 'Laksar', 'Lalkuan', 'Landaur', 'Landhaura', 'Lansdowne', 'Lohaghat', 'Mahua Dabra Haripura', 'Mahua Kheraganj', 'Manglaur', 'Muni Ki Reti', 'Mussoorie', 'Nainital', 'Nandprayag', 'Narendranagar', 'Pauri Garhwal', 'Pithoragarh', 'Ramnagar', 'Ranikhet', 'Rishikesh', 'Roorkee', 'Rudraprayag', 'Rudrapur', 'Shaktigarh', 'Sitarganj', 'Srinagar', 'Sultanpur', 'Tanakpur', 'Tehri Garhwal', 'Udham Singh Nagar', 'Uttarkashi', 'Vikasnagar', 'Virbhadra IDPL');
cities['Uttar Pradesh'] = new Array('<-----select city----->', 'Achhalda', 'Achhnera', 'Adari', 'Afzalgarh', 'Agarwal Mandi', 'Agra', 'Ahraura', 'Ailam', 'Ajhuwa', 'Akbarpur', 'Aliganj', 'Aligarh', 'Allahabad', 'Allahganj', 'Allapur', 'Amanpur', 'Ambedkar Nagar', 'Ambehta', 'Amethi', 'Amila', 'Aminagar Sarai', 'Amraudha', 'Amroha', 'Anandnagar', 'Antu', 'Anupshahr', 'Aonla', 'Ashrafpur Kichhauchha', 'Atarra', 'Atasu', 'Atrauli', 'Atrauliya', 'Auraiya', 'Aurangabad', 'Auras', 'Awagarh', 'Ayodhya', 'Azamgarh', 'Azmatgarh', 'Babarpur Ajitmal', 'Baberu', 'Babina', 'Babrala', 'Babugarh', 'Bachhraon', 'Bachhrawan', 'Badaun', 'Bagpat', 'Bah', 'Bahadurganj', 'Baheri', 'Bahjoi', 'Bahraich', 'Bahsuma', 'Bahuwa', 'Bajna', 'Bakewar', 'Bakshi Ka Talab', 'Baldeo', 'Ballia', 'Balrampur', 'Banat', 'Banda', 'Bangarmau', 'Banki', 'Bansdih', 'Bansgaon', 'Bansi', 'Barabanki', 'Baragaon', 'Baraut', 'Bareilly', 'Barhalganj', 'Barhani Bazar', 'Barkhera', 'Barsana', 'Barua Sagar', 'Barwar', 'Basti', 'Behat', 'Bela Pratapgarh', 'Belthara Road', 'Beniganj', 'Beswan', 'Bewar', 'Bhabnan Bazar', 'Bhadarsa', 'Bhadohi', 'Bhagwant Nagar', 'Bharatganj', 'Bhargain', 'Bharthana', 'Bharwari', 'Bhatni Bazar', 'Bhatpar Rani', 'Bhawan Bahadur Nagar', 'Bhinga', 'Bhogaon', 'Bhojpur Dharampur', 'Bhokarhedi', 'Bidhuna', 'Bighapur', 'Bijnor', 'Bikapur', 'Bilari', 'Bilariaganj', 'Bilaspur', 'Bilgram', 'Bilhaur', 'Bilram', 'Bilsanda', 'Bilsi', 'Bindki', 'Bisalpur', 'Bisanda Buzurg', 'Bisauli', 'Bisharatganj', 'Biswan', 'Bithoor', 'Budaun', 'Budhana', 'Bugrasi', 'Bulandshahr', 'Chail', 'Chakia', 'Chandauli', 'Chandausi', 'Chandpur', 'Charkhari', 'Charthawal', 'Chaumuhan', 'Chhaprauli', 'Chharra Rafatpur', 'Chhata', 'Chhatari', 'Chhibramau', 'Chilkana Sultanpur', 'Chirgaon', 'Chitbara Gaon', 'Chitrakoot', 'Chitrakoot Dham', 'Chopan', 'Chunar', 'Churk Ghurma', 'Colonelganj', 'Dadri', 'Dalmau', 'Dankaur', 'Dariyabad', 'Dasna', 'Dataganj', 'Daurala', 'Dayalbagh', 'Deoband', 'Deoranian', 'Deoria', 'Derapur', 'Dewa', 'Dhampur', 'Dhanaura', 'Dhaura Tanda', 'Dhaurehra', 'Dibai', 'Dibiyapur', 'Dildarnagar Fatehpur Bazar', 'Doghat', 'Dohrighat', 'Domariyaganj', 'Dostpur', 'Dudhi', 'Ekdil', 'Erich', 'Etah', 'Etawah', 'Etmadpur', 'Faizabad', 'Faizganj', 'Farah', 'Faridnagar', 'Faridpur', 'Fariha', 'Farrukhabad', 'Fatehabad', 'Fatehganj Pashchimi', 'Fatehganj Purvi', 'Fatehgarh', 'Fatehpur', 'Fatehpur Chaurasi', 'Fatehpur Sikri', 'Firozabad', 'Gajraula', 'Gangaghat', 'Gangapur', 'Gangoh', 'Ganj Dundawara', 'Ganj Muradabad', 'Garautha', 'Garhi Pukhta', 'Garhmukhteshwar', 'Gaura Barhaj', 'Gauri Bazar', 'Gawan', 'Ghatampur', 'Ghaziabad', 'Ghazipur', 'Ghiraur', 'Ghorawal', 'Ghosi', 'Ghosia Bazar', 'Ghughuli', 'Gohand', 'Gokul', 'Gola Bazar', 'Gola Gokaran Nath', 'Gonda', 'Gopamau', 'Gopiganj', 'Gorakhpur', 'Gosainganj', 'Govardhan', 'Gulaothi', 'Gulariya', 'Gulariya Bhindara', 'Gunnaur', 'Gursahaiganj', 'Gursarai', 'Gyanpur', 'Haidergarh', 'Haldaur', 'Hamirpur', 'Handia', 'Hapur', 'Hardoi', 'Harduaganj', 'Hargaon', 'Hariharpur', 'Harraiya', 'Hasanpur', 'Hasayan', 'Hastinapur', 'Hata', 'Hathgram', 'Hathras', 'Hindalco Industries Ltd','Iglas', 'Ikauna', 'Iltifatganj Bazar', 'Islamnagar', 'Itaunja', 'Jafarabad', 'Jagner', 'Jahanabad', 'Jahangirabad', 'Jahangirpur', 'Jais', 'Jaithara', 'Jalalabad', 'Jalali', 'Jalalpur', 'Jalaun', 'Jalesar', 'Jangipur', 'Jansath', 'Jarwal', 'Jasrana', 'Jaswantnagar', 'Jatari', 'Jaunpur', 'Jewar', 'Jhalu', 'Jhansi', 'Jhansi Railway Settlement', 'Jhinjhak', 'Jhinjhana', 'Jhusi', 'Jiyanpur', 'Joya', 'Jyoti Khuriya', 'Jyotiba Phule Nagar', 'Kabrai', 'Kachhauna Patseni', 'Kachhla', 'Kachhwa', 'Kadaura', 'Kadipur', 'Kaimganj', 'Kairana', 'Kakod', 'Kakori', 'Kakrala', 'Kalinagar', 'Kalpi', 'Kamalganj', 'Kampil', 'Kandhla', 'Kannauj', 'Kanpur Dehat', 'Kanpur Nagar', 'Kanshiram Nagar', 'Kanth', 'Kaptanganj', 'Karari', 'Karhal', 'Karnawal', 'Kasganj', 'Katghar Lalganj', 'Kathera', 'Katra', 'Katra Medniganj', 'Kauriaganj', 'Kaushambi', 'Kemri', 'Kerakat', 'Khadda', 'Khaga', 'Khair', 'Khairabad', 'Khalilabad', 'Khamaria', 'Khanpur', 'Kharela', 'Khargupur', 'Kharkhoda', 'Khatauli', 'Khekada', 'Kheragarh', 'Kheri', 'Khetasarai', 'Khudaganj', 'Khurja', 'Khutar', 'Kiraoali', 'Kiratpur', 'Kishni', 'Kishunpur', 'Kithaur', 'Koeripur', 'Konch', 'Kopaganj', 'Kora Jahanabad', 'Koraon', 'Kosi Kalan', 'Kotra', 'Kul Pahar', 'Kumaon', 'Kunda', 'Kundarki', 'Kunwargaon', 'Kuraoali', 'Kurara', 'Kursath', 'Kushinagar', 'Kusmara', 'Laharpur', 'Lakhimpur-UP', 'Lakhna', 'Lal Gopalganj Nindaura', 'Lalganj', 'Lalitpur', 'Lar', 'Lawar', 'Loni', 'Lucknow', 'Machhlishahr', 'Madhoganj', 'Madhogarh', 'Maghar', 'Mahaban', 'Mahamaya Nagar', 'Maharajganj', 'Mahmudabad', 'Mahoba', 'Maholi', 'Mahona', 'Mahrajganj', 'Mahroni', 'Mailani', 'Mainpuri', 'Majhauliraj', 'Malihabad', 'Mallawan', 'Mandawar', 'Manikpur', 'Manikpur Sarhat', 'Maniyar', 'Manjhanpur', 'Mankapur', 'Marehra', 'Mariahu', 'Maruadih Railway Settlement', 'Maswasi', 'Mataundh', 'Mathura', 'Mau', 'Mau Aima', 'Maudaha', 'Maunath Bhanjan', 'Mauranipur', 'Maurawan', 'Mawana', 'Meerut', 'Mehdawal', 'Mehnagar', 'Mendu', 'Milak', 'Miranpur', 'Mirganj', 'Mirzapur', 'Misrikh', 'Modinagar', 'Mogra Badshahpur', 'Mohammadabad', 'Mohammadi', 'Mohan', 'Mohanpur', 'Moradabad', 'Moth', 'Mubarakpur', 'Mughalsarai', 'Mughalsarai Railway Settlement', 'Muhammadabad', 'Mundera Bazar', 'Mundiya', 'Muradnagar', 'Mursan', 'Musafirkhana', 'Muzaffarnagar', 'Nadigaon', 'Nagina', 'Nagram', 'Nai Bazar', 'Najibabad', 'Nakur', 'Nanauta', 'Nandgaon', 'Nanpara', 'Naraini', 'Narauli', 'Naraura', 'Naugawan Sadat', 'Nautanwa', 'Nawabganj', 'Nehtaur', 'Nichlaul', 'Nidhauli Kalan', 'Niwari', 'Nizamabad','Noida', 'Noorpur', 'Nyoria Husainpur', 'Nyotini', 'Obra', 'Oel Dhakwa', 'Orai', 'Oran', 'Pachperwa', 'Padrauna', 'Pahasu', 'Paintepur', 'Pali', 'Paliya Kalan', 'Pantnagar', 'Parikshitgarh', 'Parsadepur', 'Patala', 'Patiyali', 'Patti', 'Phalauda', 'Phaphund', 'Phulpur', 'Pihani', 'Pilibhit', 'Pilkhana', 'Pilkhuwa', 'Pinahat', 'Pipiganj', 'Pipraich', 'Pipri', 'Powayan', 'Pratapgarh', 'Pukhrayan', 'Puranpur', 'Purdilnagar', 'Purquazi', 'Purwa', 'Rabupura', 'Radhakund', 'Rae Bareli', 'Raeeli', 'Railway Settlement Roza', 'Raja Ka Rampur', 'Rajapur', 'Ramkola', 'Ramnagar', 'Rampur', 'Rampur Karkhana', 'Rampur Maniharan', 'Rampura', 'Ranipur', 'Rasra', 'Rasulabad', 'Rath', 'Raya', 'Renukoot', 'Reoti', 'Richha', 'Risiya Bazar', 'Rithora', 'Roorkee', 'Rudauli', 'Rudayan', 'Rudrapur', 'Rura', 'Sadabad', 'Sadat', 'Safipur', 'Sahanpur', 'Saharanpur', 'Sahaspur', 'Sahaswan', 'Sahatwar', 'Sahawar', 'Sahjanwan', 'Sahpau', 'Saidpur', 'Sainthal', 'Saiyad Raza', 'Sakhanu', 'Sakit', 'Salempur', 'Salon', 'Sambhal', 'Samdhan', 'Samthar', 'Sandi', 'Sandila', 'Sant Kabir Nagar', 'Sant Ravidas Nagar', 'Sarai Aquil', 'Sarai Mir', 'Sardhana', 'Sarila', 'Sarsawa', 'Sasni', 'Satrikh', 'Saunkh', 'Saurikh', 'Seohara', 'Sewalkhas', 'Sewarhi', 'Shahabad', 'Shahganj', 'Shahi', 'Shahjahanpur', 'Shahpur', 'Shamli', 'Shamsabad', 'Shankargarh', 'Shergarh', 'Sherkot', 'Shikarpur', 'Shikohabad', 'Shishgarh', 'Shivli', 'Shivrajpur', 'Shohratgarh', 'Shravasti', 'Siana', 'Siddharthnagar', 'Siddhaur', 'Sidhauli', 'Sidhpura', 'Sikanderpur', 'Sikandra', 'Sikandrabad', 'Sikandrarao', 'Singahi Bhiraura', 'Sirathu', 'Sirauli', 'Sirsa', 'Sirsaganj', 'Sirsi', 'Sisauli', 'Siswa Bazar', 'Sitapur', 'Sonbhadra', 'Soron', 'Suar', 'Subeha', 'Sultanpur', 'Sumerpur', 'Suriyawan', 'Swamibagh', 'Talbehat', 'Talgram', 'Tambaur', 'Tanda', 'Thakurdwara', 'Thana Bhawan', 'Thiriya Nizamat Khan', 'Tikait Nagar', 'Tikri', 'Tilhar', 'Tindwari', 'Tirwaganj', 'Titron', 'Tondi Fatehpur', 'Tulsipur', 'Tundla', 'Ugu', 'Ujhani', 'Ujhari', 'Umri', 'Umri Kalan', 'Un', 'Unchahar', 'Unnao', 'Usawan', 'Usehat', 'Uska Bazar', 'Utraula', 'Varanasi', 'Vijaigarh', 'Vrindavan', 'Warhapur', 'Wazirganj', 'Zaidpur', 'Zamania');
cities['West Bengal'] = new Array('<-----select city----->', 'Adra', 'Alipurduar', 'Arambag', 'Asansol', 'Ashokenagar Kalyangarh', 'Baduria', 'Baharampur', 'Baidyabati', 'Bally', 'Balurghat', 'Bankura', 'Bansberia', 'Barakar', 'Baranagar', 'Barasat', 'Bardhaman', 'Barrackpore', 'Barrackpur Cantonment', 'Baruipur', 'Basirhat', 'Beldanga', 'Berhampore', 'Bhadreswar', 'Bhatpara', 'Bidhannagar', 'Birbhum', 'Birnagar', 'Bishnupur', 'Bolpur', 'Bongaon', 'Budge Budge', 'Chakdaha', 'Champdani', 'Chandannagar', 'Chandrakona', 'Contai', 'Cooch Behar', 'Coopers Camp', 'Dainhat', 'Dakshin Dinajpur', 'Dalkhola', 'Dankuni', 'Darjeeling', 'Dhulian', 'Dhupguri', 'Diamond Harbour', 'Dinhata', 'Dubrajpur', 'Dum Dum', 'Durgapur','East Midnapore', 'Egra', 'English Bazar', 'Gangarampur', 'Garulia', 'Gayespur', 'Ghatal', 'Gobardanga', 'Guskara', 'Habra', 'Haldia', 'Haldibari', 'Halisahar', 'Haora', 'Hooghly', 'Islampur', 'Jalpaiguri', 'Jamuria', 'Jangipur', 'Jaynagar Mazilpur', 'Jhalda', 'Jhargram', 'Jiaganj Azimganj', 'Kaliaganj', 'Kalimpong', 'Kalna', 'Kalyani', 'Kamarhati', 'Kanchrapara', 'Kandi', 'Katwa', 'Kharagpur', 'Kharar', 'Khardah', 'Koch Bihar', 'Kolkata', 'Konnagar', 'Krishnanagar', 'Kshirpai', 'Kulti', 'Kurseong', 'Madhyamgram', 'Maheshtala', 'Mal', 'Malda', 'Mathabhanga', 'Medinipur', 'Mekliganj', 'Memari', 'Midnapore', 'Mirik', 'Murshidabad', 'Nabadiganta Industrial Township', 'Nabadwip', 'Nadia', 'Naihati', 'Nalhati', 'New Barrackpore', 'North Barrackpore', 'North DumDum', 'Old Malda', 'Panihati', 'Panskura', 'Parganas Alipore', 'Parganas Barasat', 'Pujali', 'Purulia', 'Raghunathpur', 'Raiganj', 'Rajarhat Gopalpur', 'Rajpur Sonarpur', 'Ramjibanpur', 'Rampurhat', 'Ranaghat', 'Raniganj', 'Rishra', 'Sainthia', 'Santipur', 'Serampore', 'Siliguri', 'Sonamukhi', 'South DumDum', 'Suri', 'Taherpur', 'Taki', 'Tamluk', 'Tarakeswar', 'Titagarh', 'Tufanganj', 'Uluberia', 'Uttar Dinajpur', 'Uttarpara Kotrung');
cities['0'] = new Array('<-----Select-City----->');
/* Function to display the cities according to the state selected for Retailers*/
function retailersetcities()
{
	stateSel = document.getElementById('State');
	cityList = cities[stateSel.value];
	changeSelect('city', cityList, cityList);
}
/* Function to display the cities according to the state selected for E-Retailers*/
function eretailersetcities()
{
	stateSel = document.getElementById('Stateeret');
	cityList = cities[stateSel.value];
	changeSelect('cityeret', cityList, cityList);
}
/*Function to display the cities according to the state selected for Retailers and E-Retailers*/
function changeSelect(fieldID, newOptions, newValues)
{
	selectField = document.getElementById(fieldID);
	selectField.options.length = 0;
	for (i = 0; i < newOptions.length; i++)
	{
		selectField.options[selectField.length] = new Option(
		newOptions[i], newValues[i]);
	}
}
/*Function to show and hide the Email Id(Others) field according to the selected retailers and eretailers checkbox*/
function onclickemailid()
{
	var otheremailid=document.addretailer.emailid.value;
	document.addretailer.emailidother.value="";
	var otheremailat = "@";
	var dot = ".";
	var otheremailindexat=otheremailid.indexOf(otheremailat);
	var otheremailidlen=otheremailid.length;
	var otheremailsubstrng=otheremailid.substring(otheremailat,otheremailidlen);
	var otheremailsubstrngdot=otheremailsubstrng.indexOf(dot);
	var otheremailiddot=otheremailid.indexOf(dot);
	var hyphen = "-";
	var underscore = "_";
	var indexhyphen = otheremailid.indexOf(hyphen);
	var indexunderscore = otheremailid.indexOf(underscore);
	var regexotheremail = "!#$%^&*()+=[]\\\';/{}|\":<>?,";
	var otherEmailIddiv = "otherEmailId";
	if(otheremailid=="" || otheremailid=="amaronbatterywale@gmail.com")
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter the primary Email Id.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		var topPops=380+(0*90);
		var newToppops=topPops+"px";
		document.getElementById("popup").style.top=newToppops;
		document.getElementById("popupmessage").innerHTML=errMsg
	}
	if(otheremailid == "")
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter the primary Email Id.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if(otheremailid == "amaronbatterywale@gmail.com")
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter the primary Email Id.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if (otheremailid.indexOf(otheremailat) == -1)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Invalid E-mail ID.The \"Email Id\" Field Must Contain An \"@\" And a \".\".(e.g. free2rhyme@ngit.in)</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_email();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if (otheremailid.indexOf(otheremailat) == -1 || otheremailid.indexOf(otheremailat) == 0 || otheremailid.indexOf(otheremailat) == otheremailidlen)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Invalid E-mail ID.The \"Email Id\" Field Must Contain An \"@\" And a \".\".(e.g. free2rhyme@ngit.in)</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_email();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if((otheremailidlen-1)-otheremailsubstrngdot<2)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter valid emailid</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg			
		return ;
	}
	if (otheremailid.indexOf(dot) == -1 || otheremailid.indexOf(dot) == 0 || otheremailid.indexOf(dot) == otheremailidlen)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Invalid E-mail ID.The \"Email Id\" Field Must Contain An \"@\" And a \".\".(e.g. free2rhyme@ngit.in)</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_email();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if (otheremailid.indexOf(otheremailat, (otheremailindexat + 1)) != -1)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>You Have Entered Invalid E-mail ID.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_email();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if (otheremailid.substring(otheremailindexat - 1, otheremailindexat) == dot|| otheremailid.substring(otheremailindexat + 1, otheremailindexat + 2) == dot || otheremailid.substring(otheremailiddot+1,otheremailiddot+2)==dot)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Invalid E-mail ID.The \"Emailid\" Field Must Contain An \"@\" And a \".\".(e.g. free2rhyme@ngit.in)</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_email();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if (otheremailid.indexOf(dot, (otheremailindexat + 2)) == -1)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Invalid E-mail ID.The \"Emailid\" Field Must Contain An \"@\" And a \".\".(e.g. free2rhyme@ngi.in)</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_email();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if (otheremailid.indexOf(" ") != -1)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Invalid E-mail ID.The \"Emailid\" Field Must Contain An \"@\" And a \".\".(e.g. free2rhyme@ngit.in)</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_email();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if (otheremailid.indexOf("_@") != -1)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Invalid E-mail ID.The \"Emailid\" Field Must Contain An \"@\" And a \".\".(e.g. free2rhyme@ngit.in)</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_email();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if (otheremailid.indexOf("-@") != -1)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Invalid E-mail ID.The \"Emailid\" Field Must Contain An \"@\" And a \".\".(e.g. free2rhyme@ngit.in)</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_email();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	var regexpemail = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	var regexpemail1=/@(([^\.]*\.[^\.]*)?){1,3}$/;
	var regexpemail2=/^[a-zA-Z0-9_\+-]+(\.[a-zA-Z0-9_\+-]+)*@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.([a-zA-Z]{2,3})$/;
	if (! document.addretailer.emailid.value.match(regexpemail))
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of free2rhyme@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return ;
	}
	if (! document.addretailer.emailid.value.match(regexpemail2))
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of free2rhyme@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return ;
	}
	if (! document.addretailer.emailid.value.match(regexpemail1))
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of free2rhyme@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return ;
	}
	if (document.addretailer.emailid.value.indexOf('com.in') >=0)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of free2rhyme@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return ;
	}
	if (otheremailid.substring(indexhyphen-1,indexhyphen)==hyphen || otheremailid.substring(indexhyphen+1,indexhyphen+2)==hyphen)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of free2rhyme@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return ;
	}
	
	if ((otheremailid.substring(indexhyphen-1,indexhyphen)==hyphen || otheremailid.substring(indexunderscore+1,indexunderscore+2)==underscore) || (otheremailid.substring(indexunderscore-1,indexunderscore)==underscore || otheremailid.substring(indexhyphen+1,indexhyphen+2)==hyphen))
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of free2rhyme@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return ;
	}
	if (otheremailid.substring(indexhyphen - 1, indexhyphen) == underscore || otheremailid.substring(indexhyphen + 1, indexhyphen + 2) == underscore || otheremailid.substring(indexunderscore+1,indexunderscore+2)==underscore)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of free2rhyme@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return ;
	}
	if (otheremailid.indexOf("__") != -1)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of free2rhyme@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return ;
	}
	for (var i = 0; i < document.addretailer.emailid.value.length; i++)
	{
  		if (regexotheremail.indexOf(document.addretailer.emailid.value.charAt(i)) != -1)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Special characters are not allowed in Email-Id field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
  		}
	}
	if (document.addretailer.emailid.value.length >50)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter only 50 characters in Email-id field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return ;
	}

	if(!(otheremailid=="" || otheremailid=="amaronbatterywale@gmail.com"))
	{
		if($('#otherEmailId').is(':visible'))
		{	
			$("#"+otherEmailIddiv).hide();
		}
		else
		{	
			$("#"+otherEmailIddiv).show();
		}
	}
}
/*This function is used to dispaly LandLine Number (others) div for Retailer*/
function onclicklandlineretailer()
{
	var moreLand=document.addretailer.phone_number.value;
	document.addretailer.phone_numberother.value="";
	if(moreLand=="" || moreLand=="+918572-234545")
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter the primary LandLine Number.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_phonenumber();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		var topPops=380+(0*90);
		var newToppops=topPops+"px";
		document.getElementById("popup").style.top=newToppops;
		document.getElementById("popupmessage").innerHTML=errMsg
	}
	if(moreLand == "")
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter the primary LandLine Number</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_phonenumber();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if(moreLand == "+918572-234545")
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter the primary LandLine Number</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_phonenumber();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	var hyphen="-";
	var hyphenindex=moreLand.indexOf(hyphen);
	var otherland = "0123456789-+%2B";
	var otherlandstring = moreLand;
	var otherlandValid = true;
	for (i = 0;  i < otherlandstring.length;  i++)
	{
		for (j = 0;  j < otherland.length;  j++)
		if (otherlandstring.charAt(i) == otherland.charAt(j))
		break;
		if (j == otherland.length)
		{
			otherlandValid = false;
			break;
		}
	}
	if (!otherlandValid)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter only digits with one hyphen and one Plus in the \"Landline Number\" Field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_phonenumber();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return ;
	}
	var regland=/^(\+)?(\d{4,6}-\d{6,8})$/;
	if(!document.addretailer.phone_number.value.match(regland))
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter the Landline number in the format of +9180-12345678</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_phonenumber();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return ;
	}
	if (moreLand.substring(hyphenindex-1,hyphenindex)==hyphen || moreLand.substring(hyphenindex+1,hyphenindex+2)==hyphen)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter only digits with one hyphen and one Plus in the \"Landline Number\" Field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_phonenumber();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return ;
	}
	if (document.addretailer.phone_number.value.indexOf('--') >= 0 || document.addretailer.phone_number.value.indexOf('++') >= 0) 
	{     
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>The Landline Number should be in the format of +91-8572-236677</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_phonenumber();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if (/[0-9]{2}/i.test(document.addretailer.phone_number.value) != true) 
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Please enter only digits with one hyphen and plus in the \"Landline Number\" Field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_phonenumber();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if (document.addretailer.phone_number.value.length >16)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter only 16 numbers in Landline Number field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_phonenumber();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return ;
	}
	if (document.addretailer.phone_number.value.length <10)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter valid Landline Number.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_phonenumber();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return ;
	}
	if(!(moreLand=="" || moreLand=="+918572-234545"))
	{
		var otherLandlinediv = "otherLandline";
		
		if($('#otherLandline').is(':visible'))
		{	
			$("#"+otherLandlinediv).hide();
		}
		else
		{	
			$("#"+otherLandlinediv).show();
		}
	}
}
/*This function is used to dispaly LandLine Number (others) div for E-Retailer*/
function onclicklandlineeretailer()
{
	var otherlanderet=document.addretailer.phone_numbereret.value;
	document.addretailer.phone_numberothereret.value="";
	
	if(otherlanderet=="" || otherlanderet=="+918572-234545")
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter the primary LandLine Number.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_phnumbereret();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		var topPops=380+(0*90);
		var newToppops=topPops+"px";
		document.getElementById("popup").style.top=newToppops;
		document.getElementById("popupmessage").innerHTML=errMsg
	}
	if(otherlanderet == "")
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter the primary LandLine Number</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_phnumbereret();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if(otherlanderet == "+918572-234545")
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter the primary LandLine Number</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_phnumbereret();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	var hyphen="-";
	var hyphenindex=otherlanderet.indexOf(hyphen);
	var otherland = "0123456789-+%2B";
	var otherlandstring = otherlanderet;
	var otherlandValid = true;
	for (i = 0;  i < otherlandstring.length;  i++)
	{
		for (j = 0;  j < otherland.length;  j++)
		if (otherlandstring.charAt(i) == otherland.charAt(j))
		break;
		if (j == otherland.length)
		{
			otherlandValid = false;
			break;
		}
	}
	if (!otherlandValid)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter only digits with a hypen(-) and plus(+) in the \"Landline Number\" Field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_phnumbereret();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return ;
	}
	var regexotherland=/^(\+)?(\d{4,6}-\d{6,8}$)/;
	if(!document.addretailer.phone_numbereret.value.match(regexotherland))
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter the Landline number in the format of +9180-12345678</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_phnumbereret();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return ;
	}
	if (otherlanderet.substring(hyphenindex-1,hyphenindex)==hyphen || otherlanderet.substring(hyphenindex+1,hyphenindex+2)==hyphen)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter only digits with one hyphen and plus in the \"Landline Number\" Field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_phnumbereret();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return ;
	}
	if (document.addretailer.phone_numbereret.value.indexOf('--') >= 0 || document.addretailer.phone_numbereret.value.indexOf('++') >= 0) 
	{     
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>The Landline Number should be in the format of +918572-236677</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_phnumbereret();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}	
	if (/[0-9]{2}/i.test(document.addretailer.phone_numbereret.value) != true) 
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Please enter only digits with one hyphen and plus in the \"Landline Number\" Field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_phnumbereret();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if (document.addretailer.phone_numbereret.value.length >16)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter only 16 numbers in Landline Number field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_phnumbereret();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return ;
	}
	if (document.addretailer.phone_numbereret.value.length <10)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter valid Landline Number.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_phnumbereret();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return ;
	}
	if(!(otherlanderet=="" || otherlanderet=="+918572-234545"))
	{
		var ERetailerlandlinediv = "ERetLandlinediv";
		if($('#ERetLandlinediv').is(':visible'))
		{	
			$("#"+ERetailerlandlinediv).hide();
		}
		else
		{	
			$("#"+ERetailerlandlinediv).show();
		}
	}
}
/*This function is used to dispaly Mobile Number (others) div for Retailer*/
function onclickMobile()
{
	var othermobile=document.addretailer.mobile_number.value;
	document.addretailer.mobile_numberother.value="";
	if(othermobile=="" || othermobile=="9002265577")
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter the primary Mobile Number.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_mobile();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		var topPops=380+(0*90);
		var newToppops=topPops+"px";
		document.getElementById("popup").style.top=newToppops;
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if(othermobile == "")
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter the primary Mobile Number</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_mobile();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if(othermobile == "+919002265577")
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter the primary Mobile Number</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_mobile();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	var checkothermobile = "0123456789+%2B";
	var othermobilestring = othermobile;
	var othermobileValid = true;
	for (i = 0; i < othermobilestring.length; i++)
	{
		for (j = 0; j < checkothermobile.length; j++)
		if (othermobilestring.charAt(i) == checkothermobile.charAt(j))
		break;
		if (j == checkothermobile.length)
		{
			othermobileValid = false;
			break;
		}
	}
	if (!othermobileValid)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter only digits with a plus in the \"Mobile Number\" Field</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_mobile();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	var regular=/^(\+)?(\d{1,2})?(\d{10,11})$/;
	if(!document.addretailer.mobile_number.value.match(regular))
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter the Mobile Number in the format of +911234567809</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_mobile();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return ;
	}
	if (document.addretailer.mobile_number.value.length > 15)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please Enter only 15 Numbers</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_mobile();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if (document.addretailer.mobile_number.value.length < 10)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter valid mobile number</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_mobile();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if (document.addretailer.mobile_number.value.indexOf('++') >= 0 ) 
	{     
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>The Mobile Number should be in the format of +919857236677</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_mobile();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if(!(othermobile=="" || othermobile=="+919002265577"))
	{	
		var otherMobilediv = "otherMobile";
		if($('#otherMobile').is(':visible'))
		{	
			$("#"+otherMobilediv).hide();
		}
		else
		{	
			$("#"+otherMobilediv).show();
		}
	}
}
/*This function is used to dispaly Mobile Number (others) div for E-Retailer*/
function onclickMobileeretailer()
{
	var otherMobileeret=document.addretailer.mobile_numbereret.value;
	document.addretailer.mobile_numberothereret.value="";
	if(otherMobileeret=="" || otherMobileeret=="9008867652")
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter the primary Mobile Number.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_othrmobileeret();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		var topPops=380+(0*90);
		var newToppops=topPops+"px";
		document.getElementById("popup").style.top=newToppops;
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if(otherMobileeret == "")
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter the primary Mobile Number</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_othrmobileeret();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if(otherMobileeret == "+919008867652")
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter the primary Mobile Number</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_othrmobileeret();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	var checkMobileeret = "0123456789+%2B";
	var checkMobileeretstring = otherMobileeret;
	var MobileeretValid = true;
	for (i = 0; i < checkMobileeretstring.length; i++)
	{
		for (j = 0; j < checkMobileeret.length; j++)
		if (checkMobileeretstring.charAt(i) == checkMobileeret.charAt(j))
		break;
		if (j == checkMobileeret.length)
		{
			MobileeretValid = false;
			break;
		}
	}
	if (!MobileeretValid)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter only digits in the \"Mobile Number\" Field</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_othrmobileeret();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	var regularother=/^(\+)?(\d{1,2})?(\d{10,11})$/;
	if(!document.addretailer.mobile_numbereret.value.match(regularother))
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter the Mobile Number in the format of +911234567809</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_othrmobileeret();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return ;
	}
	if (document.addretailer.mobile_numbereret.value.length > 15)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please Enter only 15 Numbers</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_othrmobileeret();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if (document.addretailer.mobile_numbereret.value.length < 10)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter valid mobile number</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_othrmobileeret();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if (document.addretailer.mobile_numbereret.value.indexOf('++') >= 0 ) 
	{     
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>The Mobile Number should be in the format of +91-9857236677</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_othrmobileeret();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if(!(otherMobileeret=="" || otherMobileeret=="+919008867652"))
	{	
		var EretMobileDiv = "ERetMobile";
		if($('#ERetMobile').is(':visible'))
		{	
			$("#"+EretMobileDiv).hide();
		}
		else
		{	
			$("#"+EretMobileDiv).show();
		}
	}

}
/*This function is used for set cities in body load*/
function addLoadEvent(func)
{
	var oldonload = window.onload;
	if (typeof window.onload != 'function')
	{
		window.onload = func;
	}
	else
	{
		window.onload = function()
		{
			if (oldonload)
			{
				oldonload();
			}
			func();
		}
	}
}
/* Function to display the cities according to the state selected for retailers*/
addLoadEvent(function()
{
	setCities();
});
/* Function to remove spaces in a string*/
function removeSpaces(string)
{
	return string.split(' ').join('');
}
function repl(o,t,r,c){if(c==1){cs="g"}else{cs="gi"}var mp=new RegExp(t,cs);ns=o.replace(mp,r);return ns}
/*Function to add retailer in to the database*/
function onClickRetailerSubmit()
{
	var retailerloginname=document.addretailer.retailerloginname.value;
	var retname=document.addretailer.retailername.value;
	var passwd=escape(document.addretailer.password.value);
	var password=passwd.replace(/\+/g, '%2B');
	var confirmpasswd=escape(document.addretailer.password1.value);
	var confirmpassword=confirmpasswd.replace(/\+/g, '%2B');
	var emailid=document.addretailer.emailid.value;
	var otheremailid=document.addretailer.emailidother.value;
	var phoneno=document.addretailer.phone_number.value;
	var phonenumber=phoneno.replace(/\+/g, '%2B');
	var otherphonenumber=document.addretailer.phone_numberother.value;
	var phonenumberother=otherphonenumber.replace(/\+/g, '%2B');
	var mobileno=document.addretailer.mobile_number.value;
	var mbnum="+91"+mobileno;
	var mynum=mbnum.replace(/\+/g, '%2B');
	var othermobilenumber=document.addretailer.mobile_numberother.value;
	var mobilenumberother=othermobilenumber.replace(/\+/g, '%2B');
	var address1=document.addretailer.retaileraddress.value;
	var address2=document.addretailer.retaileraddress2.value;
	var tollfreenumber=document.addretailer.tollfreenumber.value;
	var timings=document.addretailer.timings.value;
	var zipcode=document.addretailer.zipcode.value;
	var city=document.addretailer.city.value;
	var state=document.addretailer.State.value;
	var area=document.addretailer.area.value;
	var dummy="yes";
	var regexretailername = "!@#$^*()+=[]\\\';,.{}|\"<>?~`";
	area=Trim(area);
	var addresstrim=escape(address1);
	var contactaddress=addresstrim.replace(/\+/g, '%2B');
	if(phonenumber=="%2B918572-234545" || phonenumber=="%2B91")
	{
		phonenumber="";
	}
	else
	{
		phonenumber=phonenumber;
	}
	if(mynum=="%2B919002265577"){
	mynum="";
	}
	else{
	mynum=mynum;
	}	
	if(address2=="www.amaronbookbattery.com" )
	{
		address2="";
	}
	else
	{
		address2=address2;
	}
	if(zipcode==500214)
	{
		zipcode="";
	}
	else
	{
		zipcode=zipcode;
	}	
	if (document.addretailer.eretailer.checked==true)
	{
		eret="1";
	}
	else
	{
		eret="0";
	}
	if (document.addretailer.State.selectedIndex <= 0)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please Choose Your \"State\" </br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_state();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if (document.addretailer.city.value == "<-----select city----->")
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please Choose Your \"City\" </br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_City();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}	
	var retailername=Trim(retname);
	retailername=repl(retailername,"&","%26",1);
	retailername=repl(retailername,"/","%2F",1);
	retailername=repl(retailername,":","%3A",1);
	retailername=repl(retailername,"_","%5F",1);
	retailername=repl(retailername,"-","%2D",1);
	if((retailername == "") || (retailername == "AmaronBatteries%2DNehruNagar%2DHyd") || (retailername == "AmaronBatteries%2BNehruNagar%2BHyd"))
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter retailer name.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_retailername();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if(document.addretailer.retailername.value.length < 3)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter at least 3 characters in the \"retailer name\" field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_retailername();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if (/[a-z][A-Z]{2}/i.test(document.addretailer.retailername.value) != true) 
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Please Enter Atleast Three Characters Together In The \"Retailer Name\" Field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_retailername();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if (document.addretailer.retailername.value.length > 50)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter only 50 characters in the \"retailer name\" field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_retailername();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if (document.addretailer.retailername.value.indexOf('--') >= 0 ) 
	{     
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>The Retailer Name should be in the format of AmaronBatteries NehruNagar Hyd</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_retailername();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	var retailernameregex = "~!@#$^*()+=[]\\\';,.{}|\"<>?";
	for (var i = 0; i < document.addretailer.retailername.value.length; i++) 
	{
		if (retailernameregex.indexOf(document.addretailer.retailername.value.charAt(i)) != -1)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Invalid Retailer Name.Please Check</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_retailername();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			document.getElementById("emailok").focus();
			return;
		}
    }
	if((retailerloginname == "")||(retailerloginname == "Amaronenterprises"))
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter retailer login name.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_retailerloginname();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if(retailerloginname == "Amaronenterprises")
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter retailer login name.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_retailerloginname();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if(document.addretailer.retailerloginname.value.length < 3)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter at least 3 characters in the \"retailer login name\" field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_retailerloginname();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if (document.addretailer.retailerloginname.value.length > 50)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter only 50 characters in the \"retailer login name\" field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_retailerloginname();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if(retailerloginname != "")
	{
		var checkretailerloginname = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
		var retailerloginnamestring = retailerloginname;
		var retailerloginnameValid = true;
		for (i = 0;  i < retailerloginnamestring.length;  i++)
		{
			for (j = 0;  j < checkretailerloginname.length;  j++)
			if (retailerloginnamestring.charAt(i) == checkretailerloginname.charAt(j))
			break;
			if (j == checkretailerloginname.length)
			{
				retailerloginnameValid = false;
				break;
			}
		}
		if(!retailerloginnameValid)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please Enter Alpha Numeric characters in the \"retailer login name\" Field without spaces.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_retailerloginname();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
	}
	var regexLetterloginname = /\d/;
	var regexNumloginname = /[a-zA-Z]/;
	if(!regexNumloginname.test(retailerloginname) && regexLetterloginname.test(retailerloginname))
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Only numerics are not allowed in Retailer Login Name field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_retailerloginname();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return ;
	}
	if(password == "")
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter password</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_password();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if (document.addretailer.password.value.length < 5)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter at least 5 characters in the \"Password\" field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_password();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if (document.addretailer.password.value.length > 30)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter only 30 characters in the \"Password\" field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_password();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if (document.addretailer.password.value.indexOf(' ') >= 0 ) 
	{     
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Please Enter Only Alphabets,Numerics and Special Characters in Password Field..</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_password();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if(confirmpassword == "")
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter Confirm Password.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_cnfrmpassword();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if (document.addretailer.password1.value.length < 5)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Password and Confirm Password are not same.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_cnfrmpassword();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return ;
	}
	if (document.addretailer.password1.value.length > 30)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Password and Confirm Password are not same.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_cnfrmpassword();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return ;
	}
	if (document.addretailer.password1.value.indexOf(' ') >= 0 ) 
	{     
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Please Enter Only Alphabets,Numerics and Special Characters in Confirm Password Field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_cnfrmpassword();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if (document.addretailer.password.value != document.addretailer.password1.value)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Password and Confirm Password are not same.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_cnfrmpassword();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return ;
	}
	if(timings!="")
	{
		var timingsreg= /^((10|11|12|[1-9]):[0-5][0-9] [APap][mM])(,(10|11|12|[1-9]):[0-5][0-9] [APap][mM])*$/;
		if (! document.addretailer.timings.value.match(timingsreg))
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter  Timings in the format of 12:59 AM,1:00 PM</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_timings();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return ;
		}
	}
	emailid=Trim(emailid);
	if(emailid == "")
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter Email-id</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if(emailid == "amaronbatterywale@gmail.com")
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter Email-id</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if(emailid!="")
	{
		var emailidat = "@";
		var emailiddot = ".";
		var emailidindexat = emailid.indexOf(emailidat);
		var emailidlen = emailid.length;
		var emailidindexdot = emailid.indexOf(emailiddot);
		var substringat = emailid.substring(emailidat,emailidlen);
		var substringindex=substringat.indexOf(emailiddot);
		var hyphen = "-";
		var underscore = "_";
		var emailidhi = emailid.indexOf(hyphen);
		var indexunderscore = emailid.indexOf(underscore);
		var regexspecialcharacters = "!#$%^&*()+=[]\\\';/{}|\":<>?,";
		if (emailid.indexOf(emailidat) == -1)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Invalid E-mail ID.The \"Email Id\" Field Must Contain An \"@\" And a \".\".(e.g. free2rhyme@ngit.in)</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		if (emailid.indexOf(emailidat) == -1 || emailid.indexOf(emailidat) == 0 || emailid.indexOf(emailidat) == emailidlen)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Invalid E-mail ID.The \"Email Id\" Field Must Contain An \"@\" And a \".\".(e.g. free2rhyme@ngit.in)</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		if((emailidlen-1)-substringindex<2)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter valid emailid</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg			
			return ;
		}
		if (emailid.indexOf(emailiddot) == -1 || emailid.indexOf(emailiddot) == 0 || emailid.indexOf(emailiddot) == emailidlen)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Invalid E-mail ID.The \"Email Id\" Field Must Contain An \"@\" And a \".\".(e.g. free2rhyme@ngit.in)</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		if (emailid.indexOf(emailidat, (emailidindexat + 1)) != -1)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>You Have Entered Invalid E-mail ID.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		if (emailid.substring(emailidindexat - 1, emailidindexat) == emailiddot|| emailid.substring(emailidindexat + 1, emailidindexat + 2) == emailiddot || emailid.substring(emailidindexdot+1,emailidindexdot+2)==emailiddot)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Invalid E-mail ID.The \"Emailid\" Field Must Contain An \"@\" And a \".\".(e.g. free2rhyme@ngit.in)</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		if (emailid.indexOf(emailiddot, (emailidindexat + 2)) == -1)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Invalid E-mail ID.The \"Emailid\" Field Must Contain An \"@\" And a \".\".(e.g. free2rhyme@ngi.in)</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		if (emailid.indexOf(" ") != -1)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Invalid E-mail ID.The \"Emailid\" Field Must Contain An \"@\" And a \".\".(e.g. free2rhyme@ngit.in)</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		var emailidregvalid = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		var emailidreg=/@(([^\.]*\.[^\.]*)?){1,3}$/;
		var emailidregexp=/^[a-zA-Z0-9_\+-]+(\.[a-zA-Z0-9_\+-]+)*@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.([a-zA-Z]{2,3})$/;
		if (! document.addretailer.emailid.value.match(emailidregvalid))
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of free2rhyme@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if (! document.addretailer.emailid.value.match(emailidregexp))
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of free2rhyme@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if (! document.addretailer.emailid.value.match(emailidreg))
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of free2rhyme@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if (document.addretailer.emailid.value.indexOf('gmail') >=0)
		{
			if (document.addretailer.emailid.value.indexOf('com')<0)
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of smith@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				return ;
			}
		}
		if (document.addretailer.emailid.value.indexOf('rediffmail') >=0)
		{
			if (document.addretailer.emailid.value.indexOf('com')<0)
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of smith@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				return ;
			}
		}
		if (document.addretailer.emailid.value.indexOf('hotmail') >=0)
		{
			if (document.addretailer.emailid.value.indexOf('com')<0)
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of smith@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				return ;
			}
		}
		if (document.addretailer.emailid.value.indexOf('ymail') >=0)
		{
			if (document.addretailer.emailid.value.indexOf('com')<0)
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of smith@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				return ;
			}
		}
		if (document.addretailer.emailid.value.indexOf('com.in') >=0)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of free2rhyme@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if (emailid.substring(emailidhi-1,emailidhi)==hyphen || emailid.substring(emailidhi+1,emailidhi+2)==hyphen)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of free2rhyme@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if ((emailid.substring(emailidhi-1,emailidhi)==hyphen || emailid.substring(indexunderscore+1,indexunderscore+2)==underscore) || (emailid.substring(indexunderscore-1,indexunderscore)==underscore || emailid.substring(emailidhi+1,emailidhi+2)==hyphen))
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of free2rhyme@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if (emailid.substring(emailidhi - 1, emailidhi) == underscore || emailid.substring(emailidhi + 1, emailidhi + 2) == underscore || emailid.substring(indexunderscore+1,indexunderscore+2)==underscore)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of free2rhyme@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if (emailid.indexOf("__") != -1)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of free2rhyme@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if (emailid.indexOf("_@") != -1)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Invalid E-mail ID.The \"Emailid\" Field Must Contain An \"@\" And a \".\".(e.g. free2rhyme@ngit.in)</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		if (emailid.indexOf("-@") != -1)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Invalid E-mail ID.The \"Emailid\" Field Must Contain An \"@\" And a \".\".(e.g. free2rhyme@ngit.in)</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		for (var i = 0; i < document.addretailer.emailid.value.length; i++)
		{
			if (regexspecialcharacters.indexOf(document.addretailer.emailid.value.charAt(i)) != -1)
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Special characters are not allowed in Email-Id field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_email();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				return;
			}
		}
		if (document.addretailer.emailid.value.length >50)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter only 50 characters in Email-id field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
	}
	var emailidother=Trim(otheremailid);
	if(otheremailid!="")	
	{
		if(emailidother == "")
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter Email-id in Email-id (others) field</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_emailother();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		if(emailidother == "amaronbatterywale@gmail.com")
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter Email-id in Email-id (others) field</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_emailother();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		var emailidsplit = emailidother.split(",");
		var emailidsplitlength = emailidsplit.length;
		for(var i=0;i<emailidsplitlength;i++)
		{
			var at = "@";
			var dot = ".";
			var otheremailidat = emailidsplit[i].indexOf(at);
			var otheremailidlen = emailidsplit[i].length;
			var emailidsplitsubstring=emailidsplit[i].substring(at,otheremailidlen);
			var otheremailiddot=emailidsplitsubstring.indexOf(dot);
			var otheremailidindexdot = emailidsplit[i].indexOf(dot);
			var regexemailother = "!#$%^&*()+=[]\\\';/{}|\":<>?";
			var regexLetter = /\d/;
			var regexNum = /[a-zA_Z]/;
			if (emailidsplit[i].indexOf(at) == -1)
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Invalid E-mail ID.The \"Email Id (others)\" Field Must Contain An \"@\" And a \".\".(e.g. free2rhyme@bookbattery.com)</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_emailother();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				return;
			}
			for (var p = 0; p < emailidsplit[i].length; p++) 
			{
				if (regexemailother.indexOf(emailidsplit[i].charAt(p)) != -1) 
				{
					errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Special characters are not allowed in Email-Id (others) field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_emailother();' class='button4'><br></td></tr>";
					document.getElementById("popup").style.display='block';
					greyout(true);
					document.getElementById("popupmessage").innerHTML=errMsg
					return;
				}
			}
			if((otheremailidlen-1)-otheremailiddot<2)
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter valid emailid in Email-id(Others) field</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_emailother();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg			
				return ;
			}
			var regexotheremail=/@(([^\.]*\.[^\.]*)?){1,3}$/;
			if (! emailidsplit[i].match(regexotheremail))
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter a valid Email Id in Email-id (others) field</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_emailother();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg			
				return ;
			}
			if (emailidsplit[i].indexOf('com.in') >=0)
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter a valid Email Id in Email-id (others) field</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_emailother();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg			
				return ;
			}
			for (var p = 0; p < emailidsplit[i].length; p++) 
			{
				if (regexemailother.indexOf(emailidsplit[i].charAt(p)) != -1) 
				{
					errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Special characters are not allowed in Email-Id (others) field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_emailother();' class='button4'><br></td></tr>";
					document.getElementById("popup").style.display='block';
					greyout(true);
					document.getElementById("popupmessage").innerHTML=errMsg
					return;
				}
			}
			if(!regexNum.test(emailidsplit[i].substring(otheremailidindexdot,otheremailidlen)) && regexLetter.test(emailidsplit[i].substring(otheremailidindexdot,otheremailidlen)))
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter a valid Email Id in Email-id (others) field</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_emailother();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				return ;
			}
			if (emailidsplit[i].indexOf(at) == -1 || emailidsplit[i].indexOf(at) == 0 || emailidsplit[i].indexOf(at) == otheremailidlen)
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Invalid E-mail ID.The \"Email Id (others)\" Field Must Contain An \"@\" And a \".\".(e.g. free2rhyme@bookbattery.com)</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_emailother();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				return;
			}
			if (emailidsplit[i].length > 50)
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Please Enter Valid E-mail Id in Email-id (others) field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_emailother();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				return;
			}
			if (emailidsplit[i].indexOf(dot) == -1 || emailidsplit[i].indexOf(dot) == 0 || emailidsplit[i].indexOf(dot) == otheremailidlen)
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Invalid E-mail ID.The \"Email Id (others)\" Field Must Contain An \"@\" And a \".\".(e.g. free2rhyme@bookbattery.com)</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_emailother();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				return;
			}
			if (emailidsplit[i].substring(otheremailidat - 1, otheremailidat) == dot|| emailidsplit[i].substring(otheremailidat + 1, otheremailidat + 2) == dot || emailidsplit[i].substring(otheremailidindexdot+1,otheremailidindexdot+2)==dot)
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Invalid E-mail ID.The \"Emailid (others)\" Field Must Contain An \"@\" And a \".\".(e.g. free2rhyme@bookbattery.com)</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_emailother();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				return;
			}
			if (emailidsplit[i].indexOf(dot, (otheremailidat + 2)) == -1)
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Invalid E-mail ID.The \"Emailid (others)\" Field Must Contain An \"@\" And a \".\".(e.g. free2rhyme@bookbattery.com)</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_emailother();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				return;
			}
			var regexpemail = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
			if (! emailidsplit[i].match(regexpemail))
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Invalid E-mail ID.The \"Emailid (others)\" Email Id Should be in the form of free2rhyme@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_emailother();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				return ;
			}
			if (emailidsplit[i].indexOf(" ") != -1)
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Invalid E-mail ID.The \"Emailid (others)\" Field Must Contain An \"@\" And a \".\".(e.g. free2rhyme@bookbattery.com)</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_emailother();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				return;
			}
			if (emailidsplit[i].indexOf("__") != -1)
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Invalid E-mail ID.The \"Emailid (others)\" Field Must Contain An \"@\" And a \".\".(e.g. free2rhyme@bookbattery.com)</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_emailother();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				return;
			}
			if (emailidsplit[i].indexOf("_@") != -1)
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Invalid E-mail ID.The \"Emailid (others)\" Field Must Contain An \"@\" And a \".\".(e.g. free2rhyme@bookbattery.com)</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_emailother();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				return;
			}
			if (emailidsplit[i].indexOf("-@") != -1)
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Invalid E-mail ID.The \"Emailid (others)\" Field Must Contain An \"@\" And a \".\".(e.g. free2rhyme@bookbattery.com)</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_emailother();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				return;
			}
		}
		if (document.addretailer.emailidother.value.length < 3)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Please Enter 3 Characters In The E-mail others  field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_emailother();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		if (document.addretailer.emailidother.value.length > 150)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Please Enter Only 150 Characters In The E-mail (Others) field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_emailother();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		if (document.addretailer.emailid.value == document.addretailer.emailidother.value)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>The Email Id in the primary and others field are same.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		var emailidothersplit = emailidother.split(",");
		var emailidother1=emailidothersplit[0];
		var emailidother2=emailidothersplit[1];
		var emailidother3=emailidothersplit[2];
		var emailidother4=emailidothersplit[3];
		var emailidother5=emailidothersplit[4];
		var emailidother6=emailidothersplit[5];
		var emailidother7=emailidothersplit[6];
		var emailidother8=emailidothersplit[7];
		if( emailidother2==undefined && emailidother3==undefined && emailidother4==undefined && emailidother5==undefined && emailidother6==undefined && emailidother7==undefined && emailidother8==undefined)
		{
			emailidother2="";
			emailidother3="retailer";
			emailidother4="retailer1";
			emailidother5="retailer2";
			emailidother6="retailer3";
			emailidother7="retailer4";
		}
		if( emailidother3==undefined && emailidother4==undefined && emailidother5==undefined && emailidother6==undefined && emailidother7==undefined && emailidother8==undefined)
		{
			emailidother3="";
			emailidother4="retailer1";
			emailidother5="retailer2";
			emailidother6="retailer3";
			emailidother7="retailer4";
		}
		if( emailidother4==undefined && emailidother5==undefined && emailidother6==undefined && emailidother7==undefined && emailidother8==undefined)
		{
			emailidother4="";
			emailidother5="retailer2";
			emailidother6="retailer3";
			emailidother7="retailer4";
			
		}
		if( emailidother5==undefined && emailidother6==undefined && emailidother7==undefined && emailidother8==undefined)
		{
			emailidother5="";
			emailidother6="retailer3";
			emailidother7="retailer4";
			
		}
		if( emailidother6==undefined && emailidother7==undefined && emailidother8==undefined)
		{
			emailidother6="";
			emailidother7="retailer4";
						
		}
		if( emailidother7==undefined && emailidother8==undefined)
		{
			emailidother7="";
									
		}
		if((emailidother1==emailidother2) || (emailidother1==emailidother3) || (emailidother1==emailidother4) || (emailidother1==emailidother5) || (emailidother1==emailidother6) || (emailidother1==emailidother7) || (emailidother1==emailidother8) ||(emailidother2==emailidother3) ||(emailidother2==emailidother4) ||(emailidother2==emailidother5)  ||(emailidother2==emailidother6)  ||(emailidother2==emailidother7) ||(emailidother2==emailidother8) || (emailidother3==emailidother4) || (emailidother3==emailidother5) || (emailidother3==emailidother6)  || (emailidother3==emailidother7)  || (emailidother3==emailidother8) ||(emailid==emailidother) || (emailid==emailidother1) || (emailid==emailidother2) || (emailid==emailidother3) || (emailid==emailidother4) || (emailid==emailidother5) || (emailid==emailidother6) || (emailid==emailidother7) || (emailid==emailidother8))
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>The Mail id you have entered is repeated. Please check</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_emailother();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		var regexemailsplit = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
		var emailidothersplit = emailidother.split(",");
		var emailidothrsplitlen= emailidothersplit.length;
		for(var i=0;i<emailidothrsplitlen;i++)
		{
			if(!regexemailsplit.test(emailidothersplit[i]))
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>You entered invalid email id in email-id(Others) field. It should be in the format of john@gmail.com,smith@gmail.com</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_emailother();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display = 'block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				return;
			}
			if (emailidothersplit[i].indexOf('gmail') >=0)
			{
				if (emailidothersplit[i].indexOf('com')<0)
				{
					errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of smith@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_emailother();' class='button4'><br></td></tr>";
					document.getElementById("popup").style.display='block';
					greyout(true);
					document.getElementById("popupmessage").innerHTML=errMsg
					return ;
				}
			}
			if (emailidothersplit[i].indexOf('rediffmail') >=0)
			{
				if (emailidothersplit[i].indexOf('com')<0)
				{
					errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of smith@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_emailother();' class='button4'><br></td></tr>";
					document.getElementById("popup").style.display='block';
					greyout(true);
					document.getElementById("popupmessage").innerHTML=errMsg
					return ;
				}
			}
			if (emailidothersplit[i].indexOf('hotmail') >=0)
			{
				if (emailidothersplit[i].indexOf('com')<0)
				{
					errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of smith@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_emailother();' class='button4'><br></td></tr>";
					document.getElementById("popup").style.display='block';
					greyout(true);
					document.getElementById("popupmessage").innerHTML=errMsg
					return ;
				}
			}
			if (emailidothersplit[i].indexOf('ymail') >=0)
			{
				if (emailidothersplit[i].indexOf('com')<0)
				{
					errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of smith@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_emailother();' class='button4'><br></td></tr>";
					document.getElementById("popup").style.display='block';
					greyout(true);
					document.getElementById("popupmessage").innerHTML=errMsg
					return ;
				}
			}
		}
	}
	if(phonenumber != "")
	{
		var phonenumberhi="-";
		var phonenumberindex=phonenumber.indexOf(phonenumberhi);
		var checkphonenumber = "0123456789-+%2B";
		var phonenumberstring = phonenumber;
		var phonenumberallValid = true;
		var regexphonenumber=/^(\+)?(\d{4,6}-\d{6,8})$/;
		for (i = 0;  i < phonenumberstring.length;  i++)
		{
			for (j = 0;  j < checkphonenumber.length;  j++)
			if (phonenumberstring.charAt(i) == checkphonenumber.charAt(j))
			break;
			if (j == checkphonenumber.length)
			{
				phonenumberallValid = false;
				break;
			}
		}
		if (!phonenumberallValid)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter only digits in the \"Landline Number\" Field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_phonenumber();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if(!document.addretailer.phone_number.value.match(regexphonenumber))
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter the Landline number in the format of +9180-12345678</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_phonenumber();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if (phonenumber.substring(phonenumberindex-1,phonenumberindex)==phonenumberhi || phonenumber.substring(phonenumberindex+1,phonenumberindex+2)==phonenumberhi)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter only digits with one hyphen and a plus in the \"Landline Number\" Field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_phonenumber();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if (/[0-9]{2}/i.test(document.addretailer.phone_number.value) != true) 
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Please enter only digits with one hyphen and a plus in the \"Landline Number\" Field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_phonenumber();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		if (document.addretailer.phone_number.value.indexOf('--') >= 0 || document.addretailer.phone_number.value.indexOf('++') >= 0 ) 
		{     
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>The Landline Number should be in the format of +918572-236677</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_phonenumber();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		if (document.addretailer.phone_number.value.length >16)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter Valid  Landline Number.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_phonenumber();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if (document.addretailer.phone_number.value.length <10)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter valid Landline Number.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_phonenumber();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
	}
	if(phonenumberother != "")
	{
		if(phonenumber == "")
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter the primary LandLine Number</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_phonenumber();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		var phonenumberotherhi="-";
		var indexphonenumberother=phonenumberother.indexOf(phonenumberotherhi);
		var checkphonenumberother = "+%2B0123456789-,";
		var phonenumberotherstring = phonenumberother;
		var phonenumberotherallValid = true;
		var regexphonenumberother=/^((\+)?(\d{4,6}-\d{6,8}))(,(\+)?(\d{4,6}-\d{6,8}))*$/;
		for (i = 0;  i < phonenumberotherstring.length;  i++)
		{
			for (j = 0;  j < checkphonenumberother.length;  j++)
			if (phonenumberotherstring.charAt(i) == checkphonenumberother.charAt(j))
			break;
			if (j == checkphonenumberother.length)
			{
				phonenumberotherallValid = false;
				break;
			}
		}
		if (!phonenumberotherallValid)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter only digits with a plus(+) and an hypen(-) in the \"Landline Number (others)\" Field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_landother();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if(!document.addretailer.phone_numberother.value.match(regexphonenumberother))
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter the Landline Number other in the format of +9180-12345678,+918572-123456</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_landother();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if (phonenumberother.substring(indexphonenumberother-1,indexphonenumberother)==phonenumberotherhi || phonenumberother.substring(indexphonenumberother+1,indexphonenumberother+2)==phonenumberotherhi)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter only digits with one hyphen and plus in the \"Landline Number (others)\" Field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_landother();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if (/[0-9]{2}/i.test(document.addretailer.phone_numberother.value) != true) 
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Please enter only digits with one hyphen and a plus in the \"Landline Number (others)\" Field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_landother();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		if (document.addretailer.phone_numberother.value.indexOf('--') >= 0 || document.addretailer.phone_numberother.value.indexOf(',,') >= 0 || document.addretailer.phone_numberother.value.indexOf('++') >= 0) 
		{     
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>The Landline Number (other) should be in the format of +918572-236677,+918572-235896</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_landother();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		if (document.addretailer.phone_numberother.value.length >50)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter only 50 numbers in Landline Number (Others) field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_landother();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if (document.addretailer.phone_numberother.value.length <10)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter valid Landline Number in Landline Number (Others) field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_landother();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if (document.addretailer.phone_number.value == document.addretailer.phone_numberother.value)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>The Land Line Number in the primary and others field are same.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_phonenumber();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if(phonenumber=="")
		{
			if(document.addretailer.phone_numberother.value!="")
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter Primary Landline Number.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_phonenumber();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
			}
		}
		var phonenumberothersplit = phonenumberother.split(",");
		var phonenumberother1=phonenumberothersplit[0];
		var phonenumberother2=phonenumberothersplit[1];
		var phonenumberother3=phonenumberothersplit[2];
		var phonenumberother4=phonenumberothersplit[3];
		if( phonenumberother2==undefined && phonenumberother3==undefined && phonenumberother4==undefined)
		{
			phonenumberother2="";
			phonenumberother3="retailer";
		}
		if( phonenumberother3==undefined && phonenumberother4==undefined)
		{
			phonenumberother3="";
		}
		if((phonenumberother1==phonenumberother2) || (phonenumberother1==phonenumberother3) || (phonenumberother1==phonenumberother4) ||(phonenumberother2==phonenumberother3) ||(phonenumberother2==phonenumberother4) || (phonenumberother3==phonenumberother4) ||(phonenumber==phonenumberother) || (phonenumber==phonenumberother1) || (phonenumber==phonenumberother2) || (phonenumber==phonenumberother3) || (phonenumber==phonenumberother4))
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>The Landline Number you have entered is repeated. Please check</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_landother();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
	
	}
	if((mynum == "" && phonenumber == "" && tollfreenumber=="") || (mynum=="%2B9002265577" && phonenumber=="" && tollfreenumber==""))
	{	
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter either mobile number or landline number or Tollfree Number.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_mobile();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	
	if (mynum != "")
	{
		var checkmobilenumber = "%2B0123456789";
		var mobilenumberstring = mynum;
		var mobilenumberValid = true;
		for (i = 0; i < mobilenumberstring.length; i++)
		{
			for (j = 0; j < checkmobilenumber.length; j++)
			if (mobilenumberstring.charAt(i) == checkmobilenumber.charAt(j))
			break;
			if (j == checkmobilenumber.length)
			{
				mobilenumberValid = false;
				break;
			}
		}
		var regular=/^(\%2B)?(\d{1,2})?(\d{10,11})$/;
		if(!mobilenumberstring.match(regular))
		{	
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter the Mobile Number in the format of +911234567809</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_mobile();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if (!mobilenumberValid)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter only digits in the \"Mobile Number\" Field</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_mobile();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		if (mynum.length > 15)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please Enter only 10 Numbers</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_mobile();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		if (mynum.length < 10)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter valid mobile number</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_mobile();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		if (mynum.indexOf('++') >= 0) 
		{     
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>The Mobile Number should be in the format of 9490236677</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_mobile();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
	}
	if (mobilenumberother != "")
	{
		var checkmobilenumberother = "+%2B0123456789,";
		var checkmobilenumberstring = mobilenumberother;
		var mobilenumberotherValid = true;
		var mobilenumberothersplit = mobilenumberother.split(",");
		var regular1=/^((\+)?(\d{1,2})?(\d{10,11}))(,(\+)?(\d{1,2})?(\d{10,11}))*$/;
		var length = mobilenumberothersplit.length;
		for(var i=0;i<length;i++)
		{
			var tempaddr=mobilenumberothersplit[i];
			tempaddr=tempaddr.replace(/\%2B/g, '+');
			var templen=tempaddr.length;
			if(templen<10 || templen>13)
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please check the mobile number (other) fields.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_mobileother();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display = 'block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				return;
			}

		
		var startNumb = tempaddr.substring(0,3);
		if(startNumb!="+91")
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Mobile Number others should start with +91</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_mobileother();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if (document.addretailer.mobile_number.value == tempaddr)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>The Mobile Number in the primary and others field are same.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_mobileother();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;

		}
		}
		if(!document.addretailer.mobile_numberother.value.match(regular1))
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please Enter the mobile number others in the format of +911234567890,+919870654321</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_mobileother();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		for (i = 0; i < checkmobilenumberstring.length; i++)
		{
			for (j = 0; j < checkmobilenumberother.length; j++)
			if (checkmobilenumberstring.charAt(i) == checkmobilenumberother.charAt(j))
			break;
			if (j == checkmobilenumberother.length)
			{
				mobilenumberotherValid = false;
				break;
			}
		}
		if (!mobilenumberotherValid)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter only digits in the \"Mobile Number (others)\" Field</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_mobileother();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		if (document.addretailer.mobile_numberother.value.length > 50)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please Enter only 50 Numbers in the \"Mobile Number (others)\" Field</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_mobileother();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		if (document.addretailer.mobile_numberother.value.length < 10)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter valid mobile number in the \"Mobile Number (others)\" Field</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_mobileother();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		if (document.addretailer.mobile_number.value == document.addretailer.mobile_numberother.value)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>The Mobile Number in the primary and others field are same.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_mobile();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if (document.addretailer.mobile_numberother.value.indexOf(',,') >= 0 || document.addretailer.mobile_numberother.value.indexOf('++') >= 0) 
		{     
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>The Mobile Number (other) should be in the format of +919490236677,+919876512348</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_mobileother();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		var mobilenumberothersplit = mobilenumberother.split(",");
		var mobilenumberother1=mobilenumberothersplit[0];
		var mobilenumberother2=mobilenumberothersplit[1];
		var mobilenumberother3=mobilenumberothersplit[2];
		var mobilenumberother4=mobilenumberothersplit[3];
		if( mobilenumberother2==undefined && mobilenumberother3==undefined && mobilenumberother4==undefined)
		{
			mobilenumberother2="";
			mobilenumberother3="retailer";
		}
		if( mobilenumberother3==undefined && mobilenumberother4==undefined)
		{
			mobilenumberother3="";
		}
		if((mobilenumberother1==mobilenumberother2) || (mobilenumberother1==mobilenumberother3) || (mobilenumberother1==mobilenumberother4) ||(mobilenumberother2==mobilenumberother3) ||(mobilenumberother2==mobilenumberother4) || (mobilenumberother3==mobilenumberother4) ||(mynum==mobilenumberother) || (mynum==mobilenumberother1) || (mynum==mobilenumberother2) || (mynum==mobilenumberother3) || (mynum==mobilenumberother4))
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>The Mobile Number you have entered is repeated. Please check</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_mobileother();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
	}
	if (tollfreenumber != "")
	{
		var checkmobilenumber = "0123456789";
		var mobilenumberstring1 = tollfreenumber;
		var mobilenumberValid1 = true;
		for (i = 0; i < mobilenumberstring1.length; i++)
		{
			for (j = 0; j < checkmobilenumber.length; j++)
			if (mobilenumberstring1.charAt(i) == checkmobilenumber.charAt(j))
			break;
			if (j == checkmobilenumber.length)
			{
				mobilenumberValid1 = false;
				break;
			}
		}
		if (!mobilenumberValid1)

		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter valid tollfree number</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_tollfree();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		if (tollfreenumber.length > 15)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter valid tollfree number</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_tollfree();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		if (tollfreenumber.indexOf('--') >= 0 || tollfreenumber.indexOf('++') >= 0 || tollfreenumber.indexOf(" ") >= 1) 
		{     
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter valid tollfree number</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_tollfree();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		if (tollfreenumber.length < 3)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter valid tollfree number</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_tollfree();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
	}
	if(address1 == "")
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter address.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_address();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if(address1 == "Near My Home Tycoon, NehruNagar")
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter address.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_address();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if (/[a-z][A-Z]{60}/i.test(address1) == true) 
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter valid address.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_address();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	var regexLetteraddress1 = /\d/;
	var regexNumaddress1 = /[a-zA-Z]/;
	if(!regexNumaddress1.test(address1) && regexLetteraddress1.test(address1))
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Only numerics,spaces and special characters are not allowed in the Address field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_address();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return ;
	}
	if (/[a-z][A-Z]{2}/i.test(document.addretailer.retaileraddress.value) != true) 
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Please Enter Atleast Three Characters Together In The \"Address\" Field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_address();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if (document.addretailer.retaileraddress.value.length > 250)
	{
		 errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please Enter only 250 characters in Address field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_address();' class='button4'><br></td></tr>";
		 document.getElementById("popup").style.display='block';
		 greyout(true);
		 document.getElementById("popupmessage").innerHTML=errMsg
		 return;
	}
	var regexLetteraddress2 = /\d/;
	var regexNumaddress2 = /[a-zA_Z]/;
	if(!regexNumaddress2.test(address2) && regexLetteraddress2.test(address2))
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Only numerics,spaces and special characters are not allowed in the Web Address field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_address2ret();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return ;
	}
	if(address2 == "")
	{

	}
	else
	{
		var checkEmail = ".";
		var checkStr = address2;
		var EmailValid = false;
		var EmailPeriod = false;
		var count=0;
		for (i = 0;  i < checkStr.length;  i++)
		{
			for (j = 0;  j < checkEmail.length;  j++)
			{
				if (checkStr.charAt(i) == checkEmail.charAt(j) && checkStr.charAt(i) == ".")
				EmailPeriod = true;
				if (EmailPeriod)
				break;
				if (j == checkEmail.length)
				break;
			}
		}
		var regexsplitaddress2 = /\d/;
		var regexNumsplitaddress2 = /[a-zA_Z]/;
		if(!regexNumsplitaddress2.test(address2) && regexsplitaddress2.test(address2))
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Only numerics or spaces or Specail Characters  are not allowed in the Website field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_address2ret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;

		}
		if (!EmailPeriod)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>The website should be in the form of www.ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_address2ret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		var retailerurl = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789:/bookbattery/-";
		var checkurlstring = address2;
		var adress2Valid = true;
		for (i = 0;  i < checkurlstring.length;  i++)
		{
			for (j = 0;  j < retailerurl.length;  j++)
			if (checkurlstring.charAt(i) == retailerurl.charAt(j))
			break;
			if (j == retailerurl.length)
			{
				adress2Valid = false;
				break;
			}
		}
		if (document.addretailer.retaileraddress2.value.length >150)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter only 150 characters in the Website field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_address2ret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		var retdot=".";
		var indexdot=address2.indexOf(retdot);
		if (address2.substring(indexdot+1,indexdot+2)==retdot)
		{	
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter a valid Website address.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_address2ret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		var slash="/";
		var indexslash=address2.indexOf(slash);
		if (address2.substring(indexslash+1,indexslash+2)==slash)
		{	
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter a valid Website address.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_address2ret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		var hifen="-";
		var indexhifen=address2.indexOf(hifen);
		if (address2.substring(indexhifen+1,indexhifen+2)==hifen)
		{

			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter a valid Website address.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_address2ret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if (address2.substring(0,3)=="www")
		{
			if(address2.charAt(3) != ".")
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter a valid Website address.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_address2ret();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				return ;
			}
		}
		if (document.addretailer.retaileraddress2.value.indexOf('w.') ==0)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter a valid Website address.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_address2ret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if(address2.charAt(0) == ".")
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter a valid Website address.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_address2ret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if (document.addretailer.retaileraddress2.value.indexOf('ww.') ==0)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter a valid Website address.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_address2ret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if (document.addretailer.retaileraddress2.value.indexOf('wwww.') >=0)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter a valid Website address.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_address2ret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if(address2.charAt(address2.length-1) == ".")
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter a valid Website address.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_address2ret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		for (i = 0;  i < checkStr.length;  i++)
		{
			if (checkStr.charAt(i) == ".")
				count++;
		}
		if(address2.substring(0,3)=="www")
		{
			var url1=address2.substring(4,address2.length);
			var url2=address2.substring(0,4);
			if(url1.indexOf(".")>=0)
			{
				
			}
			else
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter a valid Website address.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_address2ret();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				return ;
			}
		}
		if (document.addretailer.retaileraddress2.value.indexOf('..') >= 0 ) 
		{     
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter a valid Website address.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_address2ret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if(count >3)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter a valid Website address.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_address2ret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
	}
	
	if(area == "")
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter area.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_area();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if(area == "NehruNagar")
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter area.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_area();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if(area != "")
	{
		var checkarea = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789. ";
		var checkareastring = area;
		var areaValid = true;
		for (i = 0;  i < checkareastring.length;  i++)
		{
			for (j = 0;  j < checkarea.length;  j++)
			if (checkareastring.charAt(i) == checkarea.charAt(j))
			break;
			if (j == checkarea.length)
			{
				areaValid = false;
				break;
			}
		}
		if (!areaValid)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter only letters and numeric characters in the \"Area\" Field without Special Characters.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_area();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		var regexLetterarea = /\d/;
		var regexNumarea = /[a-zA-Z]/;
		if(!regexNumarea.test(area) && regexLetterarea.test(area))
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Only numerics are not allowed in the Area field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_area();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if (/[a-z][A-Z]{2}/i.test(document.addretailer.area.value) != true) 
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Please enter atleast three characters together in \"Area\" Field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_area();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		if (document.addretailer.area.value.indexOf('..') >= 0 ) 
		{     
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Please enter valid Area Name</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_area();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		if (document.addretailer.area.value.indexOf('.') == 0 ) 
		{     
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Please enter valid Area Name</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_area();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		var arearegex = "~!@#$%^_&*()+=[]\\\';,-/{}|\":<>?";
		for (var i = 0; i < document.addretailer.area.value.length; i++) 
		{
			if (arearegex.indexOf(document.addretailer.area.value.charAt(i)) != -1) 
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Special Characters are not allowed in area name field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_area();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				document.getElementById("emailok").focus();
				return;
			}
		}
	}
	if(zipcode!="")
	{
		var checkzipcode = "0123456789 ";
		var checkzipcodestring = zipcode;
		var zipcodeValid = true;
		for (i = 0;  i < checkzipcodestring.length;  i++)
		{
			for (j = 0;  j < checkzipcode.length;  j++)
			if (checkzipcodestring.charAt(i) == checkzipcode.charAt(j))
			break;
			if (j == checkzipcode.length)
			{
				zipcodeValid = false;
				break;
			}
		}
		if (!zipcodeValid)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter only Numbers and one space after 3 digits in the \"zipcode\" field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_zipcode();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		var regzipcode=/^\d{6}|\d{3}\s*\d{3}$/;
		var regzipcode1=/^[^\s\-].*[^\s\-]$/
		if(!document.addretailer.zipcode.value.match(regzipcode))
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter valid zipcode in the \"zipcode\" field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_zipcode();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if(!document.addretailer.zipcode.value.match(regzipcode1))
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter valid zipcode in the \"zipcode\" field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_zipcode();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if (document.addretailer.zipcode.value.indexOf(' ') <= 0)
		{
			if (document.addretailer.zipcode.value.length > 6)
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please Enter only 6 Numbers and a space after 3 digits in Zipcode Field</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_zipcode();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				return;
			}
		}
		else
		{
			if (document.addretailer.zipcode.value.length >7)
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please Enter only 6 Numbers and a space after 3 digits in Zipcode Field</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_zipcode();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				return;
			}
		}
		if (document.addretailer.zipcode.value.length < 6)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please Enter Atleast 6 Numbers in Zipcode Field</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_zipcode();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
	}
	if(timings=="")
	{
	timings="NA";
	}
	else
	{
	timings=timings;
	}
	$('#retsubmitbutton').hide();
	document.addretailer.method="post";
	document.addretailer.action="/bookbattery/servlet/RetailerRegistration?hidWhatToDo=addRetailer&retailerloginname="+retailerloginname+"&retailername="+retailername+"&emailid="+emailid+"&emailidother="+emailidother+"&city="+city+"&state="+state+"&zipcode="+zipcode+"&password="+password+"&phone_number="+phonenumber+"&phone_numberother="+phonenumberother+"&mobile_number="+mynum+"&mobile_numberother="+mobilenumberother+"&tollfreenumber="+tollfreenumber+"&address="+contactaddress+"&website="+address2+"&eretailer_flag="+eret+"&area="+area+"&timings="+timings+"&temp="+dummy;
	document.addretailer.submit();
}
/* Function to add e-retailer in to the database*/
function onClickERetailerSubmit()
{		
	var retailerloginname=document.addretailer.retailerloginname.value;
	var retname=document.addretailer.retailername.value;
	var password=escape(document.addretailer.password.value);
	var confirmpassword=escape(document.addretailer.password1.value);
	var eretaddress1=document.addretailer.eretaddress1.value;
	var addresstrim=escape(eretaddress1);
	var contactaddress=addresstrim.replace(/\+/g, '%2B');
	var address2=document.addretailer.eretaddress2.value;
	var phoneno=document.addretailer.phone_numbereret.value;
	var phonenumber=phoneno.replace(/\+/g, '%2B');
	var otherphonenumber=document.addretailer.phone_numberothereret.value;
	var phonenumberother=otherphonenumber.replace(/\+/g, '%2B');
	var mobileno=document.addretailer.mobile_numbereret.value;
	var mobilenumber=mobileno.replace(/\+/g, '%2B');
	var othermobilenumber=document.addretailer.mobile_numberothereret.value;
	var mobilenumberother=othermobilenumber.replace(/\+/g, '%2B');
	var zipcode=document.addretailer.zipcodeeret.value;
	var city=document.addretailer.cityeret.value;
	var state=document.addretailer.Stateeret.value;
	var area=document.addretailer.areaeret.value;
	var emailid=document.addretailer.emailid.value;
	var otheremailid=document.addretailer.emailidother.value;
	var etollfreenumber=document.addretailer.etollfreenumber.value;
	var timings=document.addretailer.timings.value;
	var dummy="yes";
	if(mobilenumber=="9008867652")
	{
		mobilenumber="";
	}
	else
	{
		mobilenumber=mobilenumber;
	}
	var mbnum="+91"+mobileno;
	var mynum=mbnum.replace(/\+/g, '%2B');
	if(mynum=="%2B919008867652")
	{
		mynum="";
	}
	else
	{
		mynum=mynum;
	}
	if(phonenumber=="%2B918572-234545")
	{
		phonenumber="";
	}
	else
	{
		phonenumber=phonenumber;
	}
	if(contactaddress=="Near My Home Tycoon, NehruNagar" || contactaddress=="Near%20My%20Home%20Tycoon%2C%20NehruNagar" )
	{
		contactaddress="";
	}
	else
	{
		contactaddress=contactaddress;
	}
	if(area=="NehruNagar")
	{
		area="";
	}
	else
	{
		area=area;
	}
	if(zipcode==500214)
	{
		zipcode="";
	}
	else
	{
		zipcode=zipcode;
	}
	if(state==0)
	{
		state="";
	}
	else
	{
		state=state;
	}
	if(city==0 || city == "<-----select city----->" || city=="<-----Select-City----->")
	{
		city="";
	}
	else
	{
		city=city;
	}
	if (document.addretailer.eretailer.checked==true)
	{
		eret="1";
	}
	else
	{
		eret="0";
	}	
	if(state!="")
	{
		if (city =="")
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please Choose Your \"City\" </br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_Cityeret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
	}
	var retailername=Trim(retname);
	retailername=repl(retailername,"&","%26",1);
	retailername=repl(retailername,"/","%2F",1);
	retailername=repl(retailername,":","%3A",1);
	retailername=repl(retailername,"_","%5F",1);
	retailername=repl(retailername,"-","%2D",1);
	if(retailername == "")
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter retailer name.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_retailername();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if(retailername == "AmaronBatteries%2DNehruNagar%2DHyd")
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter retailer name.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_retailername();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if(document.addretailer.retailername.value.length < 3)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter atleast 3 characters in the \"retailer name\" field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_retailername();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if (/[a-z][A-Z]{2}/i.test(document.addretailer.retailername.value) != true) 
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Please Enter Atleast Three Characters Together In The \"Retailer Name\" Field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_retailername();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if (document.addretailer.retailername.value.indexOf('--') >= 0 ) 
	{     
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>The Retailer Name should be in the format of AmaronBatteries-NehruNagar-Hyd/AmaronBatteries</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_retailername();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	var retailernameregex = "~!@#$^*()+=[]\\\';,.{}|\"<>?";
	for (var i = 0; i < document.addretailer.retailername.value.length; i++) 
	{
		if (retailernameregex.indexOf(document.addretailer.retailername.value.charAt(i)) != -1)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Except Hi-Fens No other Special Character or Numbers Are Not Allowed in Retailer Name Field</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_retailername();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			document.getElementById("emailok").focus();
			return;
		}
   	}
	if (document.addretailer.retailername.value.length > 50)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter only 50 characters in the \"retailer name\" field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_retailername();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	var checkretailername = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-&/-_:% ";
	var checkstringretailername = retailername;
	var retailernamevalid = true;
	for (i = 0;  i < checkstringretailername.length;  i++)
	{
		for (j = 0;  j < checkretailername.length;  j++)
		if (checkstringretailername.charAt(i) == checkretailername.charAt(j))
		break;
		if (j == checkretailername.length)
		{
			retailernamevalid = false;
			break;
		}
	}
	if (!retailernamevalid)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter only alphabets and numeric characters in the \"Retailer Name\" field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_retailername();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return ;
	}
	if(retailerloginname == "")
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter retailer login name.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_retailerloginname();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if(retailerloginname == "Amaronenterprises")
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter retailer login name.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_retailerloginname();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if(document.addretailer.retailerloginname.value.length < 3)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter at least 3 characters in the \"retailer login name\" field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_retailerloginname();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if (document.addretailer.retailerloginname.value.length > 50)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter only 50 characters in the \"retailer login name\" field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_retailerloginname();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if(retailerloginname != "")
	{
		var checkretailerloginname = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		var checkstrretailerloginname = retailerloginname;
		var retailerloginnamevalid = true;
		for (i = 0;  i < checkstrretailerloginname.length;  i++)
		{
			for (j = 0;  j < checkretailerloginname.length;  j++)
			if (checkstrretailerloginname.charAt(i) == checkretailerloginname.charAt(j))
			break;
			if (j == checkretailerloginname.length)
			{
				retailerloginnamevalid = false;
				break;
			}
		}
		if (!retailerloginnamevalid)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please Enter Only Letter and Numeric Characters in the \"retailer login name\" Field without spaces.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_retailerloginname();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
	}
	var regexLetterretailerlogin = /\d/;
	var regexNumretailerlogin = /[a-zA-Z]/;
	if(!regexNumretailerlogin.test(retailerloginname) && regexLetterretailerlogin.test(retailerloginname))
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Only numerics are not allowed in Retailer Login Name field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_retailerloginname();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return ;
	}
	if(password == "")
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter password</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_password();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if (document.addretailer.password.value.length < 5)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter at least 5 characters in the \"Password\" field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_password();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if (document.addretailer.password.value.length > 30)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter only 30 characters in the \"Password\" field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_password();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if (document.addretailer.password.value.indexOf(' ') >= 0 ) 
	{     
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Please Enter Only Alphabets,Numerics and Special Characters in Password Field..</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_password();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if(confirmpassword == "amaron")
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter Confirm Password.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_cnfrmpassword();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if (document.addretailer.password1.value.length < 5)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Password and Confirm Password are not same.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_cnfrmpassword();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return ;
	}
	if (document.addretailer.password1.value.length > 30)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Password and Confirm Password are not same.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_cnfrmpassword();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return ;
	}
	if (document.addretailer.password1.value.indexOf(' ') >= 0 ) 
	{     
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Please Enter Only Alphabets,Numerics and Special Characters in Confirm Password Field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_cnfrmpassword();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if (document.addretailer.password.value != document.addretailer.password1.value)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Password and Confirm Password are not same.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_cnfrmpassword();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return ;
	}
	if(timings!="")
	{
		var timingsreg= /^((10|11|12|[1-9]):[0-5][0-9] [APap][mM])(,(10|11|12|[1-9]):[0-5][0-9] [APap][mM])*$/;
		if (! document.addretailer.timings.value.match(timingsreg))
		{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter  Timings in the format of 12:59 AM,1:00 PM</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_timings();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return ;
		}
	}
	emailid=Trim(emailid);
	if(emailid == "")
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter Email-id</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if(emailid == "amaronbatterywale@gmail.com")
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter Email-id</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if(emailid!="")
	{
		var emailidat = "@";
		var emailiddot = ".";
		var emailidindexat = emailid.indexOf(emailidat);
		var emailidlen = emailid.length;
		var emailidindexdot = emailid.indexOf(emailiddot);
		var substringat = emailid.substring(emailidat,emailidlen);
		var substringindex=substringat.indexOf(emailiddot);
		var hyphen = "-";
		var underscore = "_";
		var emailidhi = emailid.indexOf(hyphen);
		var indexunderscore = emailid.indexOf(underscore);
		var regexspecialcharacters = "!#$%^&*()+=[]\\\';/{}|\":<>?,";
		if (emailid.indexOf(emailidat) == -1)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Invalid E-mail ID.The \"Email Id\" Field Must Contain An \"@\" And a \".\".(e.g. free2rhyme@ngit.in)</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		if (emailid.indexOf(emailidat) == -1 || emailid.indexOf(emailidat) == 0 || emailid.indexOf(emailidat) == emailidlen)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Invalid E-mail ID.The \"Email Id\" Field Must Contain An \"@\" And a \".\".(e.g. free2rhyme@ngit.in)</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		if((emailidlen-1)-substringindex<2)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter valid emailid</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg			
			return ;
		}
		if (emailid.indexOf(emailiddot) == -1 || emailid.indexOf(emailiddot) == 0 || emailid.indexOf(emailiddot) == emailidlen)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Invalid E-mail ID.The \"Email Id\" Field Must Contain An \"@\" And a \".\".(e.g. free2rhyme@ngit.in)</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		if (emailid.indexOf(emailidat, (emailidindexat + 1)) != -1)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>You Have Entered Invalid E-mail ID.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		if (emailid.substring(emailidindexat - 1, emailidindexat) == emailiddot|| emailid.substring(emailidindexat + 1, emailidindexat + 2) == emailiddot || emailid.substring(emailidindexdot+1,emailidindexdot+2)==emailiddot)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Invalid E-mail ID.The \"Emailid\" Field Must Contain An \"@\" And a \".\".(e.g. free2rhyme@ngit.in)</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		if (emailid.indexOf(emailiddot, (emailidindexat + 2)) == -1)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Invalid E-mail ID.The \"Emailid\" Field Must Contain An \"@\" And a \".\".(e.g. free2rhyme@ngi.in)</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		if (emailid.indexOf(" ") != -1)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Invalid E-mail ID.The \"Emailid\" Field Must Contain An \"@\" And a \".\".(e.g. free2rhyme@ngit.in)</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		var reg=/@(([^\.]*\.[^\.]*)?){1,3}$/;
		var regexp=/^[a-zA-Z0-9_\+-]+(\.[a-zA-Z0-9_\+-]+)*@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.([a-zA-Z]{2,3})$/;
		if (! document.addretailer.emailid.value.match(re))
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of free2rhyme@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if (! document.addretailer.emailid.value.match(regexp))
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of free2rhyme@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if (! document.addretailer.emailid.value.match(reg))
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of free2rhyme@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if (document.addretailer.emailid.value.indexOf('gmail') >=0)
		{
			if (document.addretailer.emailid.value.indexOf('com')<0)
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of smith@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				return ;
			}
		}
		if (document.addretailer.emailid.value.indexOf('rediffmail') >=0)
		{
			if (document.addretailer.emailid.value.indexOf('com')<0)
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of smith@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				return ;
			}
		}
		if (document.addretailer.emailid.value.indexOf('hotmail') >=0)
		{
			if (document.addretailer.emailid.value.indexOf('com')<0)
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of smith@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				return ;
			}
		}
		if (document.addretailer.emailid.value.indexOf('ymail') >=0)
		{
			if (document.addretailer.emailid.value.indexOf('com')<0)
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of smith@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				return ;
			}
		}
		if (document.addretailer.emailid.value.indexOf('com.in') >=0)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of free2rhyme@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if (emailid.substring(emailidhi-1,emailidhi)==hyphen || emailid.substring(emailidhi+1,emailidhi+2)==hyphen)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of free2rhyme@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if ((emailid.substring(emailidhi-1,emailidhi)==hyphen || emailid.substring(indexunderscore+1,indexunderscore+2)==underscore) || (emailid.substring(indexunderscore-1,indexunderscore)==underscore || emailid.substring(emailidhi+1,emailidhi+2)==hyphen))
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of free2rhyme@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if (emailid.substring(emailidhi - 1, emailidhi) == underscore || emailid.substring(emailidhi + 1, emailidhi + 2) == underscore || emailid.substring(indexunderscore+1,indexunderscore+2)==underscore)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of free2rhyme@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if (emailid.indexOf("__") != -1)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of free2rhyme@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if (emailid.indexOf("_@") != -1)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Invalid E-mail ID.The \"Emailid\" Field Must Contain An \"@\" And a \".\".(e.g. free2rhyme@ngit.in)</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		if (emailid.indexOf("-@") != -1)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Invalid E-mail ID.The \"Emailid\" Field Must Contain An \"@\" And a \".\".(e.g. free2rhyme@ngit.in)</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		for (var i = 0; i < document.addretailer.emailid.value.length; i++)
		{
			if (regexspecialcharacters.indexOf(document.addretailer.emailid.value.charAt(i)) != -1)
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Special characters are not allowed in Email-Id field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_email();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				return;
			}
		}
		if (document.addretailer.emailid.value.length >50)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter only 50 characters in Email-id field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
	}
	var emailidother=Trim(otheremailid);
	if(otheremailid!="")	
	{
		if(emailidother == "")
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter Email-id in Email-id (others) field</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_emailother();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		if(emailidother == "amaronbatterywale@gmail.com")
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter Email-id in Email-id (others) field</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_emailother();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		var emailidsplit = emailidother.split(",");
		var emailidsplitlength = emailidsplit.length;
		for(var i=0;i<emailidsplitlength;i++)
		{
			var otheremailidat = "@";
			var otheremailiddot = ".";
			var otheremailidindexat = emailidsplit[i].indexOf(otheremailidat);
			var otheremailidlen = emailidsplit[i].length;
			var emailidsplitsubstring=emailidsplit[i].substring(otheremailidat,otheremailidlen);
			var otheremailidsplitdot=emailidsplitsubstring.indexOf(otheremailiddot);
			var otheremailidindexdot = emailidsplit[i].indexOf(otheremailiddot);
			var regexemailother = "!#$%^&*()+=[]\\\';/{}|\":<>?";
			var regexLetteremailother = /\d/;
			var regexNumemailother = /[a-zA_Z]/;
			if (emailidsplit[i].indexOf(otheremailidat) == -1)
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Invalid E-mail ID.The \"Email Id (others)\" Field Must Contain An \"@\" And a \".\".(e.g. free2rhyme@bookbattery.com)</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_emailother();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				return;
			}
			for (var p = 0; p < emailidsplit[i].length; p++) 
			{
				if (regexemailother.indexOf(emailidsplit[i].charAt(p)) != -1) 
				{
					errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Special characters are not allowed in Email-Id (others) field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_emailother();' class='button4'><br></td></tr>";
					document.getElementById("popup").style.display='block';
					greyout(true);
					document.getElementById("popupmessage").innerHTML=errMsg
					return;
				}
			}
			var regexotheremail=/@(([^\.]*\.[^\.]*)?){1,3}$/;
			if (! emailidsplit[i].match(regexotheremail))
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter a valid Email Id in Email-id (others) field</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_emailother();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg			
				return ;
			}
			if (emailidsplit[i].indexOf('com.in') >=0)
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter a valid Email Id in Email-id (others) field</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_emailother();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg			
				return ;
			}
			if(!regexNumemailother.test(emailidsplit[i].substring(otheremailidindexdot,otheremailidlen)) && regexLetteremailother.test(emailidsplit[i].substring(otheremailidindexdot,otheremailidlen)))
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter a valid Email Id in Email-id (others) field</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_emailother();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				return ;
			}
			if (emailidsplit[i].indexOf(otheremailidat) == -1 || emailidsplit[i].indexOf(otheremailidat) == 0 || emailidsplit[i].indexOf(otheremailidat) == otheremailidlen)
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Invalid E-mail ID.The \"Email Id (others)\" Field Must Contain An \"@\" And a \".\".(e.g. free2rhyme@bookbattery.com)</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_emailother();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				return;
			}
			if((otheremailidlen-1)-otheremailidsplitdot<2)
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter valid emailid in Email-id(Others) field </br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_emailother();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg			
				return ;
			}
			if (emailidsplit[i].length > 50)
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Please Enter Valid E-mail Id in Email-id (others) field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_emailother();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				return;
			}
			if (emailidsplit[i].indexOf(otheremailiddot) == -1 || emailidsplit[i].indexOf(otheremailiddot) == 0 || emailidsplit[i].indexOf(otheremailiddot) == otheremailidlen)
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Invalid E-mail ID.The \"Email Id (others)\" Field Must Contain An \"@\" And a \".\".(e.g. free2rhyme@bookbattery.com)</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_emailother();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				return;
			}
			if (emailidsplit[i].substring(otheremailidindexat - 1, otheremailidindexat) == otheremailiddot|| emailidsplit[i].substring(otheremailidindexat + 1, otheremailidindexat + 2) == otheremailiddot || emailidsplit[i].substring(otheremailidindexdot+1,otheremailidindexdot+2)==otheremailiddot)
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Invalid E-mail ID.The \"Emailid (others)\" Field Must Contain An \"@\" And a \".\".(e.g. free2rhyme@bookbattery.com)</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_emailother();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				return;
			}
			var regexpemail = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
			if (! emailidsplit[i].match(regexpemail))
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Invalid E-mail ID.The \"Emailid (others)\" Field Must Contain An \"@\" And a \".\".(e.g. free2rhyme@bookbattery.com)</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_emailother();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				return ;
			}
			if (emailidsplit[i].indexOf(otheremailiddot, (otheremailidindexat + 2)) == -1)
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Invalid E-mail ID.The \"Emailid (others)\" Field Must Contain An \"@\" And a \".\".(e.g. free2rhyme@bookbattery.com)</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_emailother();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				return;
			}
			if (emailidsplit[i].indexOf(" ") != -1)
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Invalid E-mail ID.The \"Emailid (others)\" Field Must Contain An \"@\" And a \".\".(e.g. free2rhyme@bookbattery.com)</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_emailother();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				return;
			}
			if (emailidsplit[i].indexOf("__") != -1)
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Invalid E-mail ID.The \"Emailid (others)\" Field Must Contain An \"@\" And a \".\".(e.g. free2rhyme@bookbattery.com)</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_emailother();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				return;
			}
			if (emailidsplit[i].indexOf("_@") != -1)
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Invalid E-mail ID.The \"Emailid (others)\" Field Must Contain An \"@\" And a \".\".(e.g. free2rhyme@bookbattery.com)</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_emailother();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				return;
			}
			if (emailidsplit[i].indexOf("-@") != -1)
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Invalid E-mail ID.The \"Emailid (others)\" Field Must Contain An \"@\" And a \".\".(e.g. free2rhyme@bookbattery.com)</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_emailother();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				return;
			}
		}
		if (document.addretailer.emailidother.value.length < 3)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Please Enter 3 Characters In The E-mail others  field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_emailother();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		if (document.addretailer.emailidother.value.length > 150)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Please Enter Only 150 Characters In The E-mail (Others) field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_emailother();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		if (document.addretailer.emailid.value == document.addretailer.emailidother.value)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>The email id in the primary and others field are same.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		var emailidotherSplitResult = emailidother.split(",");
		var emailidother1=emailidotherSplitResult[0];
		var emailidother2=emailidotherSplitResult[1];
		var emailidother3=emailidotherSplitResult[2];
		var emailidother4=emailidotherSplitResult[3];
		var emailidother5=emailidotherSplitResult[4];
		var emailidother6=emailidotherSplitResult[5];
		var emailidother7=emailidotherSplitResult[6];
		var emailidother8=emailidotherSplitResult[7];
		if( emailidother2==undefined && emailidother3==undefined && emailidother4==undefined && emailidother5==undefined && emailidother6==undefined && emailidother7==undefined && emailidother8==undefined)
		{
			emailidother2="";
			emailidother3="retailer";
			emailidother4="retailer1";
			emailidother5="retailer2";
			emailidother6="retailer3";
			emailidother7="retailer4";
		}
		if( emailidother3==undefined && emailidother4==undefined && emailidother5==undefined && emailidother6==undefined && emailidother7==undefined && emailidother8==undefined)
		{
			emailidother3="";
			emailidother4="retailer1";
			emailidother5="retailer2";
			emailidother6="retailer3";
			emailidother7="retailer4";
		}
		if( emailidother4==undefined && emailidother5==undefined && emailidother6==undefined && emailidother7==undefined && emailidother8==undefined)
		{
			emailidother4="";
			emailidother5="retailer2";
			emailidother6="retailer3";
			emailidother7="retailer4";
		}
		if( emailidother5==undefined && emailidother6==undefined && emailidother7==undefined && emailidother8==undefined)
		{
			emailidother5="";
			emailidother6="retailer3";
			emailidother7="retailer4";
		}
		if( emailidother6==undefined && emailidother7==undefined && emailidother8==undefined)
		{
			emailidother6="";
			emailidother7="retailer4";
		}
		if( emailidother7==undefined && emailidother8==undefined)
		{
			emailidother7="";
		}
		if((emailidother1==emailidother2) || (emailidother1==emailidother3) || (emailidother1==emailidother4) || (emailidother1==emailidother5) || (emailidother1==emailidother6) || (emailidother1==emailidother7) || (emailidother1==emailidother8) ||(emailidother2==emailidother3) ||(emailidother2==emailidother4) ||(emailidother2==emailidother5)  ||(emailidother2==emailidother6)  ||(emailidother2==emailidother7) ||(emailidother2==emailidother8) || (emailidother3==emailidother4) || (emailidother3==emailidother5) || (emailidother3==emailidother6)  || (emailidother3==emailidother7)  || (emailidother3==emailidother8) ||(emailid==emailidother) || (emailid==emailidother1) || (emailid==emailidother2) || (emailid==emailidother3) || (emailid==emailidother4) || (emailid==emailidother5) || (emailid==emailidother6) || (emailid==emailidother7) || (emailid==emailidother8))
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>The Mail id you have entered is repeated. Please check</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_emailother();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		var regemailother = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
		for(var i=0;i<emailidsplitlength;i++)
		{
			if(!regemailother.test(emailidsplit[i]))
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>You entered invalid email id in email-id(Others) field. It should be in the format of john@gmail.com,smith@gmail.com</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_emailother();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display = 'block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				return;
			}
			if (emailidsplit[i].indexOf('gmail') >=0)
			{
				if (emailidsplit[i].indexOf('com')<0)
				{
					errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of smith@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_emailother();' class='button4'><br></td></tr>";
					document.getElementById("popup").style.display='block';
					greyout(true);
					document.getElementById("popupmessage").innerHTML=errMsg
					return ;
				}
			}
			if (emailidsplit[i].indexOf('rediffmail') >=0)
			{
				if (emailidsplit[i].indexOf('com')<0)
				{
					errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of smith@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_emailother();' class='button4'><br></td></tr>";
					document.getElementById("popup").style.display='block';
					greyout(true);
					document.getElementById("popupmessage").innerHTML=errMsg
					return ;
				}
			}
			if (emailidsplit[i].indexOf('hotmail') >=0)
			{
				if (emailidsplit[i].indexOf('com')<0)
				{
					errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of smith@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_emailother();' class='button4'><br></td></tr>";
					document.getElementById("popup").style.display='block';
					greyout(true);
					document.getElementById("popupmessage").innerHTML=errMsg
					return ;
				}
			}
			if (emailidsplit[i].indexOf('ymail') >=0)
			{
				if (emailidsplit[i].indexOf('com')<0)
				{
					errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of smith@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_emailother();' class='button4'><br></td></tr>";
					document.getElementById("popup").style.display='block';
					greyout(true);
					document.getElementById("popupmessage").innerHTML=errMsg
					return ;
				}
			}
		}
	}
	if(phonenumber != "")
	{
		var phonenumberhi="-";
		var phonenumberindex=phonenumber.indexOf(phonenumberhi);
		var checkphonenumber = "0123456789-+%2B";
		var phonenumberstring = phonenumber;
		var phonenumberallValid = true;
		for (i = 0;  i < phonenumberstring.length;  i++)
		{
			for (j = 0;  j < checkphonenumber.length;  j++)
			if (phonenumberstring.charAt(i) == checkphonenumber.charAt(j))
			break;
			if (j == checkphonenumber.length)
			{
				phonenumberallValid = false;
				break;
			}
		}
		var regexphonenumber=/^(\+)?(\d{4,6}-\d{6,8})$/;
		if(!document.addretailer.phone_numbereret.value.match(regexphonenumber))
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter the Landline number in the format of +9180-12345678</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_phnumbereret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if (!phonenumberallValid)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter only digits with a hypen(-) and a plus(+) in the \"Landline Number\" Field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_phnumbereret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if (phonenumber.substring(phonenumberindex-1,phonenumberindex)==phonenumberhi || phonenumber.substring(phonenumberindex+1,phonenumberindex+2)==phonenumberhi)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter only digits with one hyphen and a plus in the \"Landline Number\" Field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_phnumbereret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if (/[0-9]{2}/i.test(document.addretailer.phone_numbereret.value) != true) 
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Please enter only digits with one hyphen and a plus in the \"Landline Number\" Field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_phnumbereret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		if (document.addretailer.phone_numbereret.value.indexOf('--') >= 0 || document.addretailer.phone_numbereret.value.indexOf('++') >= 0) 
		{     
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>The Landline Number should be in the format of +918572-236677</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_phnumbereret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		if (document.addretailer.phone_numbereret.value.length >16)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter valid Landline Number.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_phnumbereret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if (document.addretailer.phone_numbereret.value.length <10)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter valid Landline Number.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_phnumbereret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if (document.addretailer.phone_numbereret.value == document.addretailer.phone_numberothereret.value)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>The Land Line Number in the primary and others field are same.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_phnumbereret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
	}
	if(phonenumberother != "")
	{
		if(phonenumber == "")
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter the primary LandLine Number</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_phnumbereret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		var phonenumberotherhi="-";
		var indexphonenumberother=phonenumberother.indexOf(phonenumberotherhi);
		var checkphonenumberother = "+%2B0123456789-,";
		var phonenumberotherstring = phonenumberother;
		var phonenumberotherallValid = true;
		for (i = 0;  i < phonenumberotherstring.length;  i++)
		{
			for (j = 0;  j < checkphonenumberother.length;  j++)
			if (phonenumberotherstring.charAt(i) == checkphonenumberother.charAt(j))
			break;
			if (j == checkphonenumberother.length)
			{
				phonenumberotherallValid = false;
				break;
			}
		}
		var regexphonenumberother=/^((\+)?(\d{4,6}-\d{6,8}))(,(\+)?(\d{4,6}-\d{6,8}))*$/;
		if(!document.addretailer.phone_numberothereret.value.match(regexphonenumberother))
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter the Landline Number other in the format of +9180-12345678,+918572-123456</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_landothereret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if (!phonenumberotherallValid)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter only digits with a hypen(-) and a plus(+) in the \"Landline Number (others) \" Field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_landothereret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if (phonenumberother.substring(indexphonenumberother-1,indexphonenumberother)==phonenumberotherhi || phonenumberother.substring(indexphonenumberother+1,indexphonenumberother+2)==phonenumberotherhi)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter only digits with one hyphen and a plus in the \"Landline Number (others)\" Field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_landothereret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if (document.addretailer.phone_numberothereret.value.indexOf('--') >= 0 || document.addretailer.phone_numberothereret.value.indexOf(',,') >= 0 || document.addretailer.phone_numberothereret.value.indexOf('++') >= 0) 
		{     
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>The Landline Number (other) should be in the format of +918572-236677,+918572-235896</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_landothereret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		if (/[0-9]{2}/i.test(document.addretailer.phone_numberothereret.value) != true) 
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Please enter only digits with one hyphen a plus in the \"Landline Number (others)\" Field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_landothereret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		if (document.addretailer.phone_numberothereret.value.length >50)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter only 50 numbers in Landline Number (Others) field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_landothereret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if (document.addretailer.phone_numberothereret.value.length <10)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter valid Landline Number in LandLine Number (others) field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_landothereret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if (document.addretailer.phone_numbereret.value == document.addretailer.phone_numberothereret.value)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>The Land Line Number in the primary and others field are same.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_phnumbereret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if(phonenumber=="")
		{
			if(document.addretailer.phone_numberothereret.value!="")
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter Primary Landline Number.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_phnumbereret();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				return ;
			}
		}
		var phonenumberothersplit = phonenumberother.split(",");
		var phonenumberother1=phonenumberothersplit[0];
		var phonenumberother2=phonenumberothersplit[1];
		var phonenumberother3=phonenumberothersplit[2];
		var phonenumberother4=phonenumberothersplit[3];
		if( phonenumberother2==undefined && phonenumberother3==undefined && phonenumberother4==undefined)
		{
			phonenumberother2="";
			phonenumberother3="retailer";
		}
		if( phonenumberother3==undefined && phonenumberother4==undefined)
		{
			phonenumberother3="";
		}
		if((phonenumberother1==phonenumberother2) || (phonenumberother1==phonenumberother3) || (phonenumberother1==phonenumberother4) ||(phonenumberother2==phonenumberother3) ||(phonenumberother2==phonenumberother4) || (phonenumberother3==phonenumberother4) ||(phonenumber==phonenumberother) || (phonenumber==phonenumberother1) || (phonenumber==phonenumberother2) || (phonenumber==phonenumberother3) || (phonenumber==phonenumberother4))
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>The Landline Number you have entered is repeated. Please check</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_landothereret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
	}
	if (mobilenumber != "")
	{
		var checkmobilenumber = "%2B0123456789";
		var mobilenumberstring = mynum;
		var mobilenumbervalid = true;
		for (i = 0; i < mobilenumberstring.length; i++)
		{
			for (j = 0; j < checkmobilenumber.length; j++)
			if (mobilenumberstring.charAt(i) == checkmobilenumber.charAt(j))
			break;
			if (j == checkmobilenumber.length)
			{
				mobilenumbervalid = false;
				break;
			}
		}
		var regular=/^(\%2B)?(\d{1,2})?(\d{10,11})$/;
		if(!mobilenumberstring.match(regular))
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter the Mobile Number in the format of 9603467559</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_othrmobileeret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if (!mobilenumbervalid)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter only digits in the \"Mobile Number\" Field</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_othrmobileeret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		if (document.addretailer.mobile_numbereret.value.length < 10)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter valid mobile number</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_othrmobileeret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		if (document.addretailer.mobile_numbereret.value.indexOf('--') >= 0 || document.addretailer.mobile_numbereret.value.indexOf('++') >= 0) 
		{     
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>The Mobile Number should be in the format of +919857223663</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_othrmobileeret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
	}
	if (etollfreenumber != "")
	{
		var checkmobilenumber = "0123456789";
		var mobilenumberstringe = etollfreenumber;
		var mobilenumbervalid = true;
		for (i = 0; i < mobilenumberstringe.length; i++)
		{
			for (j = 0; j < checkmobilenumber.length; j++)
			if (mobilenumberstringe.charAt(i) == checkmobilenumber.charAt(j))
			break;
			if (j == checkmobilenumber.length)
			{
				mobilenumbervalid = false;
				break;
			}
		}
		var regular=/^(\%2B)?(\d{1,2})?(\d{10,11})$/;
		if(!mobilenumberstringe.match(regular))
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter Valid Tollfree number</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_etoll();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if (!mobilenumbervalid)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter Valid Tollfree number</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_etoll();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		if (etollfreenumber.length < 3 && etollfreenumber.length > 15)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter Valid Tollfree number</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_etoll();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		if (etollfreenumber.indexOf('--') >= 0 || etollfreenumber.indexOf('++') >= 0 || etollfreenumber.indexOf(" ") >= 1) 
		{     
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Please enter Valid Tollfree number</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_etoll();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
	}
	if (mobilenumberother != "")
	{
		var checkmobilenumberother = "+%2B0123456789,";
		var checkmobilenumberstring = mobilenumberother;
		var mobilenumberothervalid = true;
		var mobilenumberothersplit = mobilenumberother.split(",");
		var mobsplitlength = mobilenumberothersplit.length;
		for(var i=0;i<mobsplitlength;i++)
		{
			var splitmobothr=mobilenumberothersplit[i];
			var splitmoblength=splitmobothr.length;
			if(splitmoblength<10 || splitmoblength>16)
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please check the mobile number (other) fields.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_mobileother1();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display = 'block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				return;
			}
		}
		for (i = 0; i < checkmobilenumberstring.length; i++)
		{
			for (j = 0; j < checkmobilenumberother.length; j++)
			if (checkmobilenumberstring.charAt(i) == checkmobilenumberother.charAt(j))
			break;
			if (j == checkmobilenumberother.length)
			{
				mobilenumberothervalid = false;
				break;
			}
		}
		var regexmobother=/^((\+)?(\d{1,2})?(\d{10,11}))(,(\+)?(\d{1,2})?(\d{10,11}))*$/;
		if(!document.addretailer.mobile_numberothereret.value.match(regexmobother))
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please Enter the mobile number others in the format of +911234567890,+919870654321</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_mobileother1();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if (!mobilenumberothervalid)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter only digits in the \"Mobile Number (others)\" Field</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_mobileother1();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		if (document.addretailer.mobile_numberothereret.value.length > 50)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please Enter only 50 Numbers in Mobile Number (others) field</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_mobileother1();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		if (document.addretailer.mobile_numberothereret.value.length < 10)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter valid mobile number in Mobile Number (others) field</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_mobileother1();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		if (document.addretailer.mobile_numberothereret.value.indexOf(',,') >= 0 || document.addretailer.mobile_numberothereret.value.indexOf('++') >= 0) 
		{     
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>The Mobile Number (other) should be in the format of +919857223665,+919572235896</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_mobileother1();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		if (document.addretailer.mobile_numbereret.value == document.addretailer.mobile_numberothereret.value)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>The Mobile Number in the primary and others field are same.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_othrmobileeret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		var mobnoothersplit = mobilenumberother.split(",");
		var mobilenumberother1=mobnoothersplit[0];
		var mobilenumberother2=mobnoothersplit[1];
		var mobilenumberother3=mobnoothersplit[2];
		var mobilenumberother4=mobnoothersplit[3];
		
		if( mobilenumberother2==undefined && mobilenumberother3==undefined && mobilenumberother4==undefined)
		{
			mobilenumberother2="";
			mobilenumberother3="retailer";
		}
		if( mobilenumberother3==undefined && mobilenumberother4==undefined)
		{
			mobilenumberother3="";
		}
		if((mobilenumberother1==mobilenumberother2) || (mobilenumberother1==mobilenumberother3) || (mobilenumberother1==mobilenumberother4) ||(mobilenumberother2==mobilenumberother3) ||(mobilenumberother2==mobilenumberother4) || (mobilenumberother3==mobilenumberother4) ||(mynum==mobilenumberother) || (mynum==mobilenumberother1) || (mynum==mobilenumberother2) || (mynum==mobilenumberother3) || (mynum==mobilenumberother4))
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>The Mobile Number you have entered is repeated. Please check</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_mobileother1();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
	}
	if (document.addretailer.eretaddress1.value == 0) 
	{

	}
	else if (/[a-z][A-Z]{2}/i.test(document.addretailer.eretaddress1.value) != true) 
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Please Enter Atleast Three Characters Together In The \"Address\" Field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_address1eret();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	var regexLettereretaddress = /\d/;
	var regexNumeretaddress = /[a-zA_Z]/;
	if(!regexNumeretaddress.test(contactaddress) && regexLettereretaddress.test(contactaddress))
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Only numerics,spaces and special characters are not allowed in the Web Address field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_address1eret();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return ;
	}
	if (/[a-z][A-Z]{60}/i.test(contactaddress) == true) 
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please Enter valid Address.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_address1eret();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if (document.addretailer.eretaddress1.value.length > 250)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please Enter only 250 characters in Address Field. </br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_address1eret();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}	
	var eretaddress2=Trim(address2);
	if(eretaddress2 == "")
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter Web Address.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_address2eret();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	else
	{
		var checkEmail = ".";
		var checkurl = eretaddress2;
		var EmailValid = false;
		var EmailPeriod = false;
		var count=0;
		for (i = 0;  i < checkurl.length;  i++)
		{
			for (j = 0;  j < checkEmail.length;  j++)
			{
				if (checkurl.charAt(i) == checkEmail.charAt(j) && checkurl.charAt(i) == ".")
				EmailPeriod = true;
				if (EmailPeriod)
				break;
				if (j == checkEmail.length)
				break;
			}
		}
		var regexLettereretaddress2 = /\d/;
		var regexNumeretaddress2 = /[a-zA_Z]/;
		if(!regexNumeretaddress2.test(eretaddress2) && regexLettereretaddress2.test(eretaddress2))
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Only numerics or spaces or Specail Characters  are not allowed in the Website field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_address2eret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if (!EmailPeriod)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>The website should be in the form of www.ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_address2eret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if(eretaddress2 == "www.amaronbookbattery.com")
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter Web Address.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_address2eret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		/*var checkeretaddress2 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789/bookbattery/-";
		var eretaddress2Valid = true;
		for (i = 0;  i < checkurl.length;  i++)
		{
			for (j = 0;  j < checkeretaddress2.length;  j++)
			if (checkurl.charAt(i) == checkeretaddress2.charAt(j))
			break;
			if (j == checkeretaddress2.length)
			{
				eretaddress2Valid = false;
				break;
			}
		}
		if (!eretaddress2Valid)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>The website should be in the form of www.ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_address2eret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			document.addretailer.eretaddress2.focus();
			return ;
		}
		if (document.addretailer.eretaddress2.value.length >150)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter only 150 characters in the Website field .</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_address2eret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		var eretdot=".";
		var indexdot=eretaddress2.indexOf(eretdot);
		if (eretaddress2.substring(indexdot+1,indexdot+2)==eretdot)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter a valid Website address.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_address2eret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		var slash="/";
		var indexslash=eretaddress2.indexOf(slash);
		if (eretaddress2.substring(indexslash+1,indexslash+2)==slash)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter a valid Website address.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_address2eret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		var hifen="-";
		var indexhifen=eretaddress2.indexOf(hifen);
		if (eretaddress2.substring(indexhifen+1,indexhifen+2)==hifen)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter a valid Website address.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_address2eret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}*/
		if (eretaddress2.substring(0,3)=="www")
		{
			if(eretaddress2.charAt(3) != ".")
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter a valid Website address.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_address2eret();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				return ;
			}
		}
		if (document.addretailer.eretaddress2.value.indexOf('w.') ==0)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter a valid Website address.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_address2eret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if(eretaddress2.charAt(0) == ".")
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter a valid Website address.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_address2eret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if (document.addretailer.eretaddress2.value.indexOf('ww.') ==0)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter a valid Website address.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_address2eret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if (document.addretailer.eretaddress2.value.indexOf('wwww.') >=0)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter a valid Website address.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_address2eret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if(eretaddress2.charAt(eretaddress2.length-1) == ".")
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter a valid Website address.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_address2eret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		for (i = 0;  i < checkurl.length;  i++)
		{
			if (checkurl.charAt(i) == ".")
				count++;
		}
		if(eretaddress2.substring(0,3)=="www")
		{
			var url1=eretaddress2.substring(4,eretaddress2.length);
			var url2=eretaddress2.substring(0,4);
			if(url1.indexOf(".")>=0)
			{
				
			}
			else
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter a valid Website address.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_address2eret();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				return ;
			}
		}
		if (document.addretailer.eretaddress2.value.indexOf('..') >= 0 ) 
		{     
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter a valid Website address.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_address2eret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if(count >3)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter a valid Website address.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_address2eret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
	}
	
	if(area != "")
	{
		var checkarea = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789. ";
		var checkstringarea = area;
		var areaValid = true;
		for (i = 0;  i < checkstringarea.length;  i++)
		{
			for (j = 0;  j < checkarea.length;  j++)
			if (checkstringarea.charAt(i) == checkarea.charAt(j))
			break;
			if (j == checkarea.length)
			{
				areaValid = false;
				break;
			}
		}
		if (!areaValid)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter only letters and numeric characters in the \"Area\" Field without Special Characters.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_areaeret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		var regexLetterarea = /\d/;
		var regexNumarea = /[a-zA-Z]/;
		if(!regexNumarea.test(area) && regexLetterarea.test(area))
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Only numerics are not allowed in the Area field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_areaeret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if (/[a-z][A-Z]{2}/i.test(document.addretailer.areaeret.value) != true) 
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Please enter atleast three characters together in \"Area\" Field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_areaeret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		if (document.addretailer.areaeret.value.indexOf('..') >= 0 ) 
		{     
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Please enter valid area name</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_areaeret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		var eretarearegex = "~!@#$%^_&*()+=[]\\\';,-/{}|\":<>?";
		for (var i = 0; i < document.addretailer.areaeret.value.length; i++) 
		{
			if (eretarearegex.indexOf(document.addretailer.areaeret.value.charAt(i)) != -1) 
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Special Characters are not allowed in area name field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_areaeret();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				document.getElementById("emailok").focus();
				return;
			}
		}
	}
	if( zipcode!="")
	{
		var checkzipcode = "0123456789 ";
		var checkstringzipcode = zipcode;
		var zipcodeValid = true;
		for (i = 0;  i < checkstringzipcode.length;  i++)
		{
			for (j = 0;  j < checkzipcode.length;  j++)
			if (checkstringzipcode.charAt(i) == checkzipcode.charAt(j))
			break;
			if (j == checkzipcode.length)
			{
				zipcodeValid = false;
				break;
			}
		}
		if (!zipcodeValid)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter only Numbers in the \"zipcode\" field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_zipcodeeret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		var regzipcode=/^\d{6}|\d{3}\s*\d{3}$/;
		var regzipcodeother=/^[^\s\-].*[^\s\-]$/
		if(!document.addretailer.zipcodeeret.value.match(regzipcode))
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter valid zipcode in the \"zipcode\" field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_zipcodeeret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		if(!document.addretailer.zipcodeeret.value.match(regzipcodeother))
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter valid zipcode in the \"zipcode\" field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_zipcodeeret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		if(document.addretailer.zipcodeeret.value.indexOf(' ') <= 0)
		{
			if (document.addretailer.zipcodeeret.value.length >6)
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please Enter only 6 Numbers with a space after 3 digits in Zipcode Field</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_zipcodeeret();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				return;
			}
		}
		else
		{
			if (document.addretailer.zipcodeeret.value.length >7)
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please Enter only 6 Numbers with a space after 3 digits in Zipcode Field</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_zipcodeeret();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				return;
			}
		}
		if (document.addretailer.zipcodeeret.value.length <6)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please Enter Atleast 6 Digits in Zipcode Field</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_zipcodeeret();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
	}
	if(timings=="")
	{
	timings="NA";
	}
	else
	{
	timings=timings;
	}
	$('#eretsubmitbutton').hide();
	eretaddress2=escape(eretaddress2);
	document.addretailer.method="post";
	document.addretailer.action="/bookbattery/servlet/RetailerRegistration?hidWhatToDo=addRetailer&retailerloginname="+retailerloginname+"&retailername="+retailername+"&emailid="+emailid+"&emailidother="+emailidother+"&city="+city+"&state="+state+"&zipcode="+zipcode+"&password="+password+"&phone_number="+phonenumber+"&phone_numberother="+phonenumberother+"&mobile_number="+mynum+"&mobile_numberother="+mobilenumberother+"&address="+contactaddress+"&tollfreenumber="+etollfreenumber+"&website="+eretaddress2+"&eretailer_flag="+eret+"&area="+area+"&timings="+timings+"&temp="+dummy;
	document.addretailer.submit();
}
/* Function to close popup and focus on retailer login name field*/
function close_popup_retailerloginname()
{
	$('#popup').hide();
	greyout(false);
	document.addretailer.retailerloginname.focus();
	document.addretailer.retailerloginname.style.color="#000000";
}
/* Function to close popup and focus on retailer name field*/
function close_popup_retailername()
{
	$('#popup').hide();
	greyout(false);
	document.addretailer.retailername.focus();
	document.addretailer.retailername.style.color="#000000";
}
/*Function to close popup and focus on mobile number other field for eretailer*/
function close_popup_othrmobileeret()
{
	$('#popup').hide();
	greyout(false);
	document.addretailer.mobile_numbereret.focus();
	document.addretailer.mobile_numbereret.style.color="#000000";
}
function close_popup_etoll()
{
	$('#popup').hide();
	greyout(false);
	document.addretailer.etollfreenumber.focus();
	document.addretailer.etollfreenumber.style.color="#000000";
}
/*Function to close popup and focus on phone number other field for eretailer*/
function close_popup_phnumbereret()
{
	$('#popup').hide();
	greyout(false);
	document.addretailer.phone_numbereret.focus();
	document.addretailer.phone_numbereret.style.color="#000000";
}
/*Function to close popup and focus on zip code for eretailer*/
function close_popup_zipcodeeret()
{
	$('#popup').hide();
	greyout(false);
	document.addretailer.zipcodeeret.focus();
	document.addretailer.zipcodeeret.style.color="#000000";
}
/* Function to close popup and focus on state field for retailer*/
function close_popup_state()
{
	$('#popup').hide();
	greyout(false);
	document.addretailer.State.focus();
	document.addretailer.State.style.color="#000000";
}
/* Function to close popup and focus on city field for retailer*/
function close_popup_City()
{
	$('#popup').hide();
	greyout(false);
	document.addretailer.city.focus();
	document.addretailer.city.style.color="#000000";
}
/* Function to close popup and focus on zipcode field for retailer*/
function close_popup_zipcode()
{
	$('#popup').hide();
	greyout(false);
	document.addretailer.zipcode.focus();
	document.addretailer.element[inputTextboxId7].value="";
	document.addretailer.zipcode.style.color="#000000";
}
/* Function to close popup and focus on mobile number field for retailer*/
function close_popup_mobile()
{
	$('#popup').hide();
	greyout(false);
	document.addretailer.mobile_number.focus();
	document.addretailer.mobile_number.style.color="#000000";
}
function close_popup_tollfree()
{
	$('#popup').hide();
	greyout(false);
	document.addretailer.tollfreenumber.focus();
	document.addretailer.tollfreenumber.style.color="#000000";
}
/* Function to close popup and focus on address1 field for retailer */
function close_popup_address()
{
	$('#popup').hide();
	greyout(false);
	document.addretailer.retaileraddress.focus();
	document.addretailer.retaileraddress.style.color="#000000";
}
/* Function to close popup and focus on address2 field for retailer*/
function close_popup_address2ret()
{
	$('#popup').hide();
	greyout(false);
	document.addretailer.retaileraddress2.focus();
	document.addretailer.retaileraddress2.style.color="#000000";
}
/* Function to close popup and focus on address1 field for eretailer*/
function close_popup_address1eret()
{
	$('#popup').hide();
	greyout(false);
	document.addretailer.eretaddress1.focus();
	document.addretailer.eretaddress1.style.color="#000000";
}
/* Function to close popup and focus on address2 field for eretailer*/
function close_popup_address2eret()
{
	$('#popup').hide();
	greyout(false);
	document.addretailer.eretaddress2.focus();
	document.addretailer.eretaddress2.style.color="#000000";
}
/* Function to close popup and focus on password field*/
function close_popup_password()
{
	$('#popup').hide();
	greyout(false);
	document.addretailer.password.focus();
	document.addretailer.password.style.color="#000000";
}
/* Function to close popup and focus on confirm password field*/
function close_popup_cnfrmpassword()
{
	$('#popup').hide();
	greyout(false);
	document.addretailer.password1.focus();
	document.addretailer.password1.style.color="#000000";
}
/* Function to close popup and focus on phone number field for retailer*/
function close_popup_phonenumber()
{
	$('#popup').hide();
	greyout(false);
	document.addretailer.phone_number.focus();
	document.addretailer.phone_number.style.color="#000000";
}
/* Function to close popup and focus on emaildid field*/
function close_popup_email()
{
	$('#popup').hide();
	greyout(false);
	document.addretailer.emailid.focus();
	document.addretailer.emailid.style.color="#000000";
}
/*Function to close popup and focus on city field for eretailer*/
function close_popup_Cityeret()
{
	$('#popup').hide();
	greyout(false);
	document.addretailer.cityeret.focus();
	document.addretailer.cityeret.style.color="#000000";
}
/* Function to close popup and focus on area field for retailer*/
function close_popup_area()
{
	$('#popup').hide();
	greyout(false);
	document.addretailer.area.focus();
	document.addretailer.area.style.color="#000000";
}
/*Function to close popup and focus on area of eretailer*/
function close_popup_areaeret()
{
	$('#popup').hide();
	greyout(false);
	document.addretailer.areaeret.focus();
	document.addretailer.areaeret.style.color="#000000";
}
/*Function to close popup and focus on mobile number other field of retailer*/
function close_popup_mobileother()
{
	$('#popup').hide();
	greyout(false);
	document.addretailer.mobile_numberother.focus();
}
/*Function to close popup and focus on land number other of eretailer*/
function close_popup_landothereret()
{
	$('#popup').hide();
	greyout(false);
	document.addretailer.phone_numberothereret.focus();
}
/*Function to close popup and focus on mobile number other of eretailer*/
function close_popup_mobileother1()
{
	$('#popup').hide();
	greyout(false);
	document.addretailer.mobile_numberothereret.focus();
}
/*Function to close popup and focus on Land number other of retailer*/
function close_popup_landother()
{
	$('#popup').hide();
	greyout(false);
	document.addretailer.phone_numberother.focus();
}
/*Function to close popup and focus on email id of eretailer*/
function close_popup_emailother()
{
	$('#popup').hide();
	greyout(false);
	document.addretailer.emailidother.focus();
}
/* Function to check retailer name*/
function retailernamecheck()
{	
	var retnamediv = "Checkretailernamediv";
	var areanamediv = "Checkareanamediv";
	if($("#"+areanamediv).is(':visible'))
	{
		$("#"+areanamediv).hide();
		$("#"+retnamediv).show();
	}
	var retailername=document.addretailer.retailername.value;
	var retlength=document.addretailer.retailername.value.length;
	var state="";
	var city="";
	var eret="";
	if (document.addretailer.eretailer.checked==true)
	{
		eret="1";
		state=document.addretailer.State.value;
		city=document.addretailer.city.value;
	}
	else
	{
		eret="0";
		state=document.addretailer.State.value;
		city=document.addretailer.city.value;
	}
	var retname=retailername.substring(0,1);
	if(retname==" ")
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Invalid Retailer Name.Please Check</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_retailername();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		document.getElementById("emailok").focus();
		return;
	}
	retailername=Trim(retailername);
	var retailernameregex = "#^";
	for (var i = 0; i < document.addretailer.retailername.value.length; i++) 
	{
		if (retailernameregex.indexOf(document.addretailer.retailername.value.charAt(i)) != -1) 
		{
			document.addretailer.retailername.value="";
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Retailer Name consists of special characters.Please check.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_retailername();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			document.getElementById("emailok").focus();
			return;
		}
  	}
  	retailername=repl(retailername,"&","%26",1);
	retailername=repl(retailername,"/","%2F",1);
	retailername=repl(retailername,":","%3A",1);
	retailername=repl(retailername,"_","%5F",1);
	retailername=repl(retailername,"-","%2D",1);
	var xmlhttp= "";
	var resp= "";
	if (window.XMLHttpRequest)
	{
		xmlhttp=new XMLHttpRequest();
	}
	else
	{
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange=function()
	{
		if (xmlhttp.readyState==4)
		{
			if(xmlhttp.status!=200)
			{
				alert("Please wait while retailer name is verifying.");
				return;
			}
			else
			{
				resp = xmlhttp.responseText;
				document.getElementById("Checkretailernamediv").innerHTML = resp;
				return;
			}
		}			
	}
	if(retlength<3)
	{
		$("#"+retnamediv).hide();
		$('#Checkareanamediv').hide();
		$("#"+retnamediv).html("");
	}
	else
	{
		$("#"+retnamediv).show();
		$('#Checkareanamediv').hide();
		xmlhttp.open("POST","/bookbattery/servlet/RetailerRegistration?hidWhatToDo=checkretailername&retailername="+retailername+"&eretailerflag="+eret+"&state="+state+"&city="+city+"&requestno="+(Math.random()*100),true);		
		xmlhttp.send();	
	}
}
/*This fuction is used when admin logout click on browser back button page redirect to admin login page*/
function noBack()
{
	window.history.forward(-3)
	session.invalidate();
	window.location.href="/bookbattery/jsp/admin/batterystore/batteryadminlogin.jsp";
}
window.onpageshow = function(evt) { if (evt.persisted) noBack() }
window.onunload = function() { void (0) }
/*Function to check retailer name and display session message*/
function checkretailername()
{
	var retailername=document.addretailer.retailername.value;
	retailername=repl(retailername,"&","%26",1);
	retailername=repl(retailername,"/","%2F",1);
	retailername=repl(retailername,":","%3A",1);
	retailername=repl(retailername,"_","%5F",1);
	retailername=repl(retailername,"-","%2D",1);
	var xmlhttp= "";
	var resp= "";
	var state="";
	var city="";
	var eret="";
	if (document.addretailer.eretailer.checked==true)
	{
		eret="1";
		state=document.addretailer.State.value;
		city=document.addretailer.city.value;
	}
	else
	{
		eret="0";
		state=document.addretailer.State.value;
		city=document.addretailer.city.value;
	}
	if (window.XMLHttpRequest)
	{
		xmlhttp=new XMLHttpRequest();
	}
	else
	{
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange=function()
	{
		if (xmlhttp.readyState==4)
		{
			if(xmlhttp.status!=200)
			{
				alert("Please wait while retailer name is verifying.");
				return;
			}
			else
			{
				resp = xmlhttp.responseText;
				if(resp.indexOf("No Retailer Name exists u can continue")>=0)
				{
					document.getElementById("divsesmsg").innerHTML = resp;
					return;
				}
				else if(resp.indexOf("Retailer Name already exists")>=0)
				{
					document.getElementById("divsesmsg").innerHTML = resp;
					watermarkretailername('inputTextboxId',' ');
					document.addretailer.retailername.value="";
					errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Retailer Name Already Exist.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_retailername();' class='button4'><br></td></tr>";
					document.getElementById("popup").style.display='block';
					greyout(true);
					document.getElementById("popupmessage").innerHTML=errMsg
					document.getElementById("emailok").focus();
					return;
				}
			}
		}			
	}
	xmlhttp.open("POST","/bookbattery/servlet/RetailerRegistration?hidWhatToDo=checkretailernamemessage&retailername="+retailername+"&state="+state+"&city="+city+"&eretailerfalg="+eret+"&requestno="+(Math.random()*100),true);		
	xmlhttp.send();
}
//added code to check retailer name after selecting city in drop down field
function checkcityretname()
{
	var retailername=document.addretailer.retailername.value;
	retailername=repl(retailername,"&","%26",1);
	retailername=repl(retailername,"/","%2F",1);
	retailername=repl(retailername,":","%3A",1);
	retailername=repl(retailername,"_","%5F",1);
	retailername=repl(retailername,"-","%2D",1);
	if((retailername == "") || (retailername == "AmaronBatteries%2DNehruNagar%2DHyd") || (retailername == "AmaronBatteries%2BNehruNagar%2BHyd"))
	{
	}
	else
	{
		var xmlhttp= "";
		var resp= "";
		var state="";
		var city="";
		var eret="";
		if (document.addretailer.eretailer.checked==true)
		{
			eret="1";
			state=document.addretailer.State.value;
			city=document.addretailer.city.value;
		}
		else
		{
			eret="0";
			state=document.addretailer.State.value;
			city=document.addretailer.city.value;
		}
		if (window.XMLHttpRequest)
		{
			xmlhttp=new XMLHttpRequest();
		}
		else
		{
			xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlhttp.onreadystatechange=function()
		{
			if (xmlhttp.readyState==4)
			{
				if(xmlhttp.status!=200)
				{
					alert("Please wait while retailer name is verifying.");
					return;
				}
				else
				{
					resp = xmlhttp.responseText;
					if(resp.indexOf("No Retailer Name exists u can continue")>=0)
					{
						document.getElementById("divsesmsg").innerHTML = resp;
						return;
					}
					else if(resp.indexOf("Retailer Name already exists")>=0)
					{
						document.getElementById("divsesmsg").innerHTML = resp;
						watermarkretailername('inputTextboxId',' ');
						document.addretailer.retailername.value="";
						errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Retailer Name Already Exist.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_retailername();' class='button4'><br></td></tr>";
						document.getElementById("popup").style.display='block';
						greyout(true);
						document.getElementById("popupmessage").innerHTML=errMsg
						document.getElementById("emailok").focus();
						return;
					}
				}
			}			
		}
		xmlhttp.open("POST","/bookbattery/servlet/RetailerRegistration?hidWhatToDo=checkretailernamemessage&retailername="+retailername+"&state="+state+"&city="+city+"&eretailerfalg="+eret+"&requestno="+(Math.random()*100),true);		
		xmlhttp.send();
	}
}


/* Function to check retailer login name and display a session message*/
function checkretailerloginname()
{	
	var retailerloginname=document.addretailer.retailerloginname.value;
	var xmlhttp= "";
	var resp= "";
	if (window.XMLHttpRequest)
	{
		xmlhttp=new XMLHttpRequest();
	}
	else
	{
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange=function()
	{
		if (xmlhttp.readyState==4)
		{
			if(xmlhttp.status!=200)
			{
				alert("Please wait while retailer login name is verifying.");
				return;
			}
			else
			{
				resp = xmlhttp.responseText;
				if(resp.indexOf("No Retailer Login Name exists u can continue")>=0)
				{
					document.getElementById("divsesmsgretlogin").innerHTML = resp;
					return;
				}
				else if(resp.indexOf("Retailer Login Name already exists")>=0)
				{
					document.getElementById("divsesmsgretlogin").innerHTML = resp;
					watermarkretailername('inputTextboxId',' ');
					document.addretailer.retailerloginname.value="";
					errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Retailer Login Name Already Exist.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_retailerloginname();' class='button4'><br></td></tr>";
					document.getElementById("popup").style.display='block';
					greyout(true);
					document.getElementById("popupmessage").innerHTML=errMsg
					document.getElementById("emailok").focus();
					return;
				}
			}
		}			
	}
	xmlhttp.open("POST","/bookbattery/servlet/RetailerRegistration?hidWhatToDo=checkretailerloginname&retailerloginname="+retailerloginname+"&requestno="+(Math.random()*100),true);		
	xmlhttp.send();
}
/* Function to show the area names in the div for retailer*/
function areanamecheck()
{	
	var retnamediv = "Checkretailernamediv";
	var areanamediv = "Checkareanamediv";     
	if($("#"+retnamediv).is(':visible'))
	{
		$("#"+retnamediv).hide();
		$("#"+areanamediv).show();
	}
	var area=document.addretailer.area.value;
	var state=document.addretailer.State.value;
	var flag="ret";
	var xmlhttp= "";
	var resp= "";
	var arealen=document.addretailer.area.value.length;
	var arearet=area.substring(0,1);
	var city=document.addretailer.city.value;
	if(city==0 || city == "<-----select city----->")
	{
		city="0";
	}
	else
	{
		city=city;
	}
	if(arearet==" ")
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Area name consists of space. Please check</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_area();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		document.getElementById("emailok").focus();
		return;
	}
	if (window.XMLHttpRequest)
	{
		xmlhttp=new XMLHttpRequest();
	}
	else
	{
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange=function()
	{
		if (xmlhttp.readyState==4)
		{
			if(xmlhttp.status!=200)
			{
				alert("Please wait while area name is verifying.");
				return;
			}
			else
			{
				resp = xmlhttp.responseText;
				document.getElementById("Checkareanamediv").innerHTML = resp;
				return;	
			}
		}			
	}
	if(arealen<3)
	{
		$("#"+areanamediv).hide();
		$("#Checkretailernamediv").hide();
		$("#"+areanamediv).html("");
	}
	else
	{
		$("#"+areanamediv).show();
		xmlhttp.open("POST","/bookbattery/servlet/RetailerRegistration?hidWhatToDo=areanamecheck&flag="+flag+"&state="+state+"&areaname="+area+"&city="+city+"&requestno="+(Math.random()*100),true);	
		xmlhttp.send();	
	}
}
/* Function to take the area name when user clicks on the area for retailer*/
function onClickAreaname(DisplayArea)
{	
	document.addretailer.area.value=DisplayArea;
}
/* Function to area nanme check of eretailer to show the list in div for eretailer*/
function areanamecheckeretailer()
{
	var retnamediv = "Checkretailernamediv";
	var areanamediv = "Checkareanamediv";     
	if($("#"+retnamediv).is(':visible'))
	{
		$("#"+retnamediv).hide();
		$("#"+areanamediv).show();
	}
	var area=document.addretailer.areaeret.value;
	var state=document.addretailer.Stateeret.value;
	var flag="eret";
	var xmlhttp= "";
	var resp= "";
	var areaeretlen=document.addretailer.areaeret.value.length;
	var city=document.addretailer.cityeret.value;
	if(city==0 || city == "<-----select city----->")
	{
		city="0";
	}
	else
	{
		city=city;
	}
	var arearet=area.substring(0,1);
	if(arearet==" ")
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Area name consists of space. Please check</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_areaeret();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		document.getElementById("emailok").focus();
		return;
	}
	if (window.XMLHttpRequest)
	{
		xmlhttp=new XMLHttpRequest();
	}
	else
	{
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange=function()
	{
		if (xmlhttp.readyState==4)
		{
			if(xmlhttp.status!=200)
			{
				alert("Please wait while area name is verifying");
				return;
			}
			else
			{
				resp = xmlhttp.responseText;
				document.getElementById("Checkareanamediv").innerHTML = resp;
				return;	
			}
		}			
	}
	if(areaeretlen <3)
	{
		$("#"+areanamediv).hide();
		$("#"+areanamediv).html("");
	}
	else
	{
		$("#"+areanamediv).show();
		xmlhttp.open("POST","/bookbattery/servlet/RetailerRegistration?hidWhatToDo=areanamecheck&flag="+flag+"&state="+state+"&areaname="+area+"&city="+city+"&requestno="+(Math.random()*100),true);		
		xmlhttp.send();	
	}
}
/* Function to take the area name when user clicks on the area for eretailer*/
function onClickAreanameeret(DisplayArea)
{	
	document.addretailer.areaeret.value=DisplayArea;
}
/*This function is used for remove water mark in Retailer name field*/
function watermarkretailername(inputId,text)
{
	var inputBox = document.getElementById(inputId);
	if (inputBox.value.length > 0)
	{
		if (inputBox.value == text)
		inputBox.value = '';
		document.addretailer.retailername.style.color="#000000"
	}
	else
	{
		inputBox.value="";
	}
}
/*This function is used for remove water mark in Retailer Email id field*/
function watermarkemailid(inputId,text)
{
	var inputBox = document.getElementById(inputId);
	if (inputBox.value.length > 0)
	{
		if (inputBox.value == text)
		inputBox.value = '';
		document.addretailer.emailid.style.color="#000000"
	}
	else
	{
		inputBox.value = text;
		inputBox.style.color = "DarkGray";
	}
}
/*This function is used for remove water mark in Retailer land line number field*/
function watermarklandline(inputId,text)
{
	var inputBox = document.getElementById(inputId);
	if (inputBox.value.length > 0)
	{
		if (inputBox.value == text)
		inputBox.value = '';
		document.addretailer.phone_number.style.color="#000000"
	}
	else
	{
		inputBox.value = text;
		inputBox.style.color = "DarkGray";
	}
}
/*This function is used for remove water mark in Retailer mobile number field*/
function watermarkmobilenumber(inputId,text)
{
	var inputBox = document.getElementById(inputId);
	if (inputBox.value.length > 0)
	{
		if (inputBox.value == text)
		inputBox.value = '';
		document.addretailer.mobile_number.style.color="#000000"
	}
	else
	{
		inputBox.value = text;
		inputBox.style.color = "DarkGray";
	}
}
/*This function is used for remove water mark in Retailer contact address field*/
function watermarkaddress(inputId,text)
{
	var inputBox = document.getElementById(inputId);
	if (inputBox.value.length > 0)
	{
		if (inputBox.value == text)
		inputBox.value = '';
		document.addretailer.retaileraddress.style.color="#000000"
	}
	else
	{
		inputBox.value = text;
		inputBox.style.color = "DarkGray";
	}
}
/*This function is used for remove water mark in Retailer web address(url) field*/
function watermarkurl(inputId,text)
{
	var inputBox = document.getElementById(inputId);
	if (inputBox.value.length > 0)
	{
		if (inputBox.value == text)
		inputBox.value = '';
		document.addretailer.retaileraddress2.style.color="#000000"
	}
	else
	{
		inputBox.value = text;
		inputBox.style.color = "DarkGray";
	}
}
/*This function is used for remove water mark in Retailer land line number field*/
function watermarkarea(inputId,text)
{
	var inputBox = document.getElementById(inputId);
	if (inputBox.value.length > 0)
	{
		if (inputBox.value == text)
		inputBox.value = '';
		document.addretailer.area.style.color="#000000"
	}
	else
	{
		inputBox.value = text;
		inputBox.style.color = "DarkGray";
	}
}
/*This function is used for remove water mark in Retailer zipcode field*/
function watermarkzipcode(inputId,text)
{
	var inputBox = document.getElementById(inputId);
	if (inputBox.value.length > 0)
	{
		if (inputBox.value == text)
		inputBox.value = '';
		document.addretailer.zipcode.style.color="#000000"
	}
	else
	{
		inputBox.value = text;
		inputBox.style.color = "DarkGray";
	}
}
/*This function is used for remove water mark in E-Retailer Land Line number field*/
function watermarklandlineeret(inputId,text)
{
	var inputBox = document.getElementById(inputId);
	if (inputBox.value.length > 0)
	{
		if (inputBox.value == text)
		inputBox.value = '';
		document.addretailer.phone_numbereret.style.color="#000000"
	}
	else
	{
		inputBox.value = text;
		inputBox.style.color = "DarkGray";
	}
}
/*This function is used for remove water mark in E-Retailer mobile number field*/
function watermarkmobileeret(inputId,text)
{
	var inputBox = document.getElementById(inputId);
	if (inputBox.value.length > 0)
	{
		if (inputBox.value == text)
		inputBox.value = '';
		document.addretailer.mobile_numbereret.style.color="#000000"
	}
	else
	{
		inputBox.value = text;
		inputBox.style.color = "DarkGray";
	}
}
/*This function is used for remove water mark in E-Retailer contact address field*/
function watermarkaddresseret(inputId,text)
{
	var inputBox = document.getElementById(inputId);
	if (inputBox.value.length > 0)
	{
		if (inputBox.value == text)
		inputBox.value = '';
		document.addretailer.eretaddress1.style.color="#000000"
	}
	else
	{
		inputBox.value = text;
		inputBox.style.color = "DarkGray";
	}
}
/*This function is used for remove water mark in E-Retailer web address(url) field*/
function watermarkereturl(inputId,text)
{
	var inputBox = document.getElementById(inputId);
	if (inputBox.value.length > 0)
	{
		if (inputBox.value == text)
		inputBox.value = '';
		document.addretailer.eretaddress2.style.color="#000000"
	}
	else
	{
		inputBox.value = text;
		inputBox.style.color = "DarkGray";
	}
}
/*This function is used for remove water mark in E-Retailer area field*/
function watermarkeretarea(inputId,text)
{
	var inputBox = document.getElementById(inputId);
	if (inputBox.value.length > 0)
	{
		if (inputBox.value == text)
		inputBox.value = '';
		document.addretailer.areaeret.style.color="#000000"
	}
	else
	{
		inputBox.value = text;
		inputBox.style.color = "DarkGray";
	}
}
/*This function is used for remove water mark in E-Retailer zip code field*/
function watermarkeretzipcode(inputId,text)
{
	var inputBox = document.getElementById(inputId);
	if (inputBox.value.length > 0)
	{
		if (inputBox.value == text)
		inputBox.value = '';
		document.addretailer.zipcodeeret.style.color="#000000"
	}
	else
	{
		inputBox.value = text;
		inputBox.style.color = "DarkGray";
	}
}
/*This function is used for remove water mark in retailer login name field*/
function watermarkretloginname(inputId,text)
{
	var inputBox = document.getElementById(inputId);
	if (inputBox.value.length > 0)
	{
		if (inputBox.value == text)
		inputBox.value = '';
		document.addretailer.retailerloginname.style.color="#000000"
	}
	else
	{
		inputBox.value="";
	}
}
/*This function is used to hide email id, land line number, mobile number other fields div's when page is loaded*/
function hideMoreFields()
{
	$('#otherEmailId').hide();
	$('#otherLandline').hide();
	$('#otherMobile').hide();
	$('#ERetMobile').hide();
	$('#ERetLandlinediv').hide();
}
/*This function is used to close the popup*/
function closePopup()
{
	$('#popup').hide();
	greyout(false);
}
/*To reset the page when clicks con browser back button*/
$(function() {
	$('#thisform')[0].reset();
});
/*Function to show and hide the eretailers field according to the selected eretailers checkbox*/
function eretailercheck()
{
	var retailerdiv = "retailerdiv";
	var eretailerdiv = "eretailerdiv";
	if($("#"+retailerdiv).is(':visible'))
	{
		$("#"+retailerdiv).hide();
		$("#"+eretailerdiv).show();
		$('#eretstate').show();
		$('#retstate').hide();
	}
	else
	{
		$("#"+retailerdiv).show();
		$("#"+eretailerdiv).hide();
		$('#retstate').show();
		$('#eretstate').hide();
	}
}
/*Function to  hide the eretailers field when the page is loaded*/
function onloaderetailerhide()
{
	$('#eretailerdiv').hide();
	$('#retstate').show();
	$('#eretstate').hide();
}
/*Function to  trim the office number*/
function Trim(officeno)
{
    while (officeno.substring(0,1) == ' ') // check for white spaces from beginning
    {
        officeno = officeno.substring(1, officeno.length);
    }
    while (officeno.substring(officeno.length-1, officeno.length) == ' ') // check white space from end
    {
        officeno = officeno.substring(0,officeno.length-1);
    }
    return officeno;
}
/*Function to  trim the retailer login name*/
function Trim(retailerloginname)
{
    while (retailerloginname.substring(0,1) == ' ') // check for white spaces from beginning
    {
        retailerloginname = retailerloginname.substring(1, retailerloginname.length);
    }
    while (retailerloginname.substring(retailerloginname.length-1, retailerloginname.length) == ' ') // check white space from end
    {
        retailerloginname = retailerloginname.substring(0,retailerloginname.length-1);
    }
    return retailerloginname;
}
/*Function to  trim the Mobile number field*/
function Trim(mobile)
{
    while (mobile.substring(0,1) == ' ') // check for white spaces from beginning
    {
        mobile = mobile.substring(1, mobile.length);
    }
    while (mobile.substring(mobile.length-1, mobile.length) == ' ') // check white space from end
    {
        mobile = mobile.substring(0,mobile.length-1);
    }
    return mobile;
}
/*Function to  trim the Zipcode field*/
function Trim(zipcode)
{
    while (zipcode.substring(0,1) == ' ') // check for white spaces from beginning
    {
        zipcode = zipcode.substring(1, zipcode.length);
    }
    while (zipcode.substring(zipcode.length-1, zipcode.length) == ' ') // check white space from end
    {
        zipcode = zipcode.substring(0,zipcode.length-1);
    }
    return zipcode;
}
/*Function to  trim the area field*/
function Trim(area)
{
    while (area.substring(0,1) == ' ') // check for white spaces from beginning
    {
        area = area.substring(1, area.length);
    }
    while (area.substring(area.length-1, area.length) == ' ') // check white space from end
    {
        area = area.substring(0,area.length-1);
    }
    return area;
}
/*Function to  focus on the retailername filed when the page is loaded*/
function onfocus()
{
	var retailername=document.addretailer.retailername.value;
	if(retailername=="")
	{
		document.addretailer.retailername.focus();
		return;
	}
	else
	{
		document.addretailer.password.focus();
		return;
	}
}
function close_popup_timings()
{
	$('#popup').hide();
	greyout(false);
	document.modifyretailer.timings.focus();
	document.modifyretailer.timings.style.color="#000000";
}
