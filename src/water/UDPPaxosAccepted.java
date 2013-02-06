package water;

/**
 * A Paxos packet: an Accepted packet
 *
 * @author <a href="mailto:cliffc@0xdata.com"></a>
 * @version 1.0
 */
public class UDPPaxosAccepted extends UDP {
  @Override AutoBuffer call(AutoBuffer ab) {
    Paxos.State t = ab.get(Paxos.State.class);
    Paxos.doAccepted(t, ab._h2o);
    return ab;
  }

  // Build an Accepted packet.  It has the membership, plus proposal
  static void build_and_multicast(Paxos.State state) {
    new AutoBuffer(H2O.SELF).putUdp(udp.paxos_accepted).put(state).close();
  }

  // Pretty-print bytes 1-15; byte 0 is the udp_type enum
  public String print16( AutoBuffer ab ) {
    ab.getPort();               // 3 bytes, 16-3 == 13 left
    // We sent a 'state' object; break down the next 13 bytes of it.
    int  typeMap = ab.get2();     // 2 byte , 13-2 == 11 left; TypeMap for 'Paxos.state'
    long promise = ab.get8();     // 8 bytes, 11-8 ==  3 left
    long oldProposal = ab.get3(); // 3 bytes,  3-3 == All gone!
    return "Promise# "+promise+" _oldProp# "+oldProposal;
  }
}
